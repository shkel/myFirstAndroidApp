package com.example.galleryn.galleryn.utils;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.galleryn.galleryn.GalleryViewAdapter;
import com.example.galleryn.galleryn.ImagePreviewLoader;
import com.example.galleryn.galleryn.R;
import com.example.galleryn.galleryn.disk.DiskUtils;
import com.example.galleryn.galleryn.disk.FilesResourceList;
import com.example.galleryn.galleryn.disk.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static java.lang.Math.ceil;

public class GalleryUtils {

    public static int getImagesPerScreen(@NonNull Configuration config, @NonNull Resources mResources) {
        return getImagesPerScreen(
                mResources.getInteger(R.integer.gallerySpanCount),
                mResources.getDisplayMetrics().density,
                mResources.getDimension(R.dimen.image_height),
                config.screenHeightDp
        );
    }

    public static void loadAndSetImagesCount(RequestHelper requestHelper, final SharedPreferencesHelper sharedPref, final Resources mResources, final TextView view) {
        if (view == null) return;
        DiskUtils.DiskFilesCountApi api = requestHelper
                .getRetrofit(DiskUtils.diskFilesUrl, RxJava2CallAdapterFactory.create())
                .create(DiskUtils.DiskFilesCountApi.class);

        Observable<FilesResourceList> observable = api.filesList(Integer.MAX_VALUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Consumer<FilesResourceList>() {
            @Override
            public void accept(FilesResourceList filesResourceList) throws Exception {
                int imagesCount = GalleryUtils.getImagesCount(filesResourceList);
                if (imagesCount > 0) {
                    sharedPref.setImagesCount(imagesCount);
                    String text = imagesCount + " "
                            + mResources.getString(R.string.images) + " "
                            + mResources.getString(R.string.galleryInfo);
                    view.setText(text);
                }
            }
        });
    }

    public static void loadFirstPage(SharedPreferencesHelper sharedPref, final Resources mResources,
                               final GalleryViewAdapter adapter, RequestHelper requestHelper,
                               final ImagePreviewLoader.LoaderError onError
    ) {
        if (adapter != null) {
            ImagePreviewLoader.LoaderCallback callback = new ImagePreviewLoader.LoaderCallback() {
                @Override
                public void exec(@Nullable GalleryImage image) {
                    if (image != null) {
                        adapter.addImageInfo(image);
                        adapter.notifyDataSetChanged();
                    } else {
                        onError.exec(mResources.getString(R.string.imageNull));
                    }
                }
            };

            new ImagePreviewLoader(mResources, requestHelper, callback, onError)
                    .loadPreviewPage(0, sharedPref.getImagesPerScreen() * 2);
        }
    }

    private static int getImagesCount(FilesResourceList filesResourceList) {
        List<Resource> listFiles = filesResourceList != null ? filesResourceList.getItems() : null;
        return listFiles != null ? listFiles.size() : 0;
    }

    public static int getImagesPerScreen(int minCount, float density, float dimension, int screenHeightDp) {
        float imageHeightDp = density != 0 ? dimension / density : 0;
        int imagesPerScreen = imageHeightDp != 0 ? (int) ceil(screenHeightDp / imageHeightDp) * 2 : 0;
        return imagesPerScreen < minCount ? minCount : imagesPerScreen;
    }

    public static void bindViewAndTime(final Handler handler, final TextView view) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setText(new SimpleDateFormat("dd MMMM, HH:mm", Locale.getDefault()).format(new Date()));
                handler.postDelayed(this, 1000);
            }
        }, 10);
    }


}
