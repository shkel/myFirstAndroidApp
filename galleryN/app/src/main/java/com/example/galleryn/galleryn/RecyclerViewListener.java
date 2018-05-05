package com.example.galleryn.galleryn;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.galleryn.galleryn.utils.GalleryImage;
import com.example.galleryn.galleryn.utils.RequestHelper;
import com.example.galleryn.galleryn.utils.SharedPreferencesHelper;

import java.util.concurrent.atomic.AtomicInteger;


public class RecyclerViewListener extends RecyclerView.OnScrollListener {
    private GridLayoutManager imageLayoutManager;
    private AtomicInteger loadingCount = new AtomicInteger(0);
    private ImagePreviewLoader loader;
    private SharedPreferencesHelper sharedPref;

    RecyclerViewListener(
            @NonNull final GridLayoutManager imageLayoutManager,
            @NonNull final GalleryViewAdapter adapter,
            @NonNull Resources resources,
            @NonNull SharedPreferencesHelper sharedPref,
            @NonNull RequestHelper requestHelper,
            @NonNull ImagePreviewLoader.LoaderError onError
    ) {
        this.imageLayoutManager = imageLayoutManager;
        this.sharedPref = sharedPref;

        ImagePreviewLoader.LoaderCallback callback = new ImagePreviewLoader.LoaderCallback() {
            @Override
            public void exec(@Nullable GalleryImage image) {
                addImage(image, adapter);
            }
        };

        loader = new ImagePreviewLoader(resources, requestHelper, callback, onError);
    }

    @Override
    public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
        // TODO: progressview
        if (dy > 0 && loadingCount.get() < 1) loadNextPage();
        super.onScrolled(recyclerView, dx, dy);
    }

    private void addImage(@Nullable GalleryImage image, GalleryViewAdapter adapter) {
        if (image != null) {
            adapter.addImageInfo(image);
            adapter.notifyItemInserted(imageLayoutManager.getItemCount());
        }
        loadingCount.decrementAndGet();
    }

    private void loadNextPage() {
        int totalItemCount = imageLayoutManager.getItemCount();
        if (isEndOfList(totalItemCount)) {
            int count = sharedPref.getImagesPerScreen() / 2;
            if (count > 0) {
                loadingCount.set(count);
                loader.loadPreviewPage(totalItemCount, count);
            }
        }
    }

    private boolean isEndOfList(int totalItemCount) {
        int visibleItemCount = imageLayoutManager.getChildCount();
        int pastVisibleItems = imageLayoutManager.findFirstVisibleItemPosition();
        return (visibleItemCount + pastVisibleItems) >= totalItemCount;
    }
}
