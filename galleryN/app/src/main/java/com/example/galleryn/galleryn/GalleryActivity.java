package com.example.galleryn.galleryn;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.galleryn.galleryn.utils.ErrorDialog;
import com.example.galleryn.galleryn.utils.GalleryUtils;
import com.example.galleryn.galleryn.utils.ImageHelper;
import com.example.galleryn.galleryn.utils.RequestHelper;
import com.example.galleryn.galleryn.utils.SharedPreferencesHelper;

public class GalleryActivity extends AppCompatActivity implements GalleryViewAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private SharedPreferencesHelper sharedPref;
    private RequestHelper requestHelper = new RequestHelper();
    private Resources mResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mResources = getResources();
        sharedPref = new SharedPreferencesHelper(this);
        setImagesCount(mResources.getConfiguration());

        ImagePreviewLoader.LoaderError onError = makeErrorFn();
        initRecyclerView(onError);

        GalleryViewAdapter adapter = recyclerView != null ? (GalleryViewAdapter) recyclerView.getAdapter() : null;
        GalleryUtils.loadFirstPage(sharedPref, mResources, adapter, requestHelper, onError);

        TextView tvHeader = findViewById(R.id.tvHeader);
        GalleryUtils.loadAndSetImagesCount(requestHelper, sharedPref, mResources, tvHeader);

        final Handler handler = new Handler(getMainLooper());
        TextView tvDate = findViewById(R.id.tvDate);
        GalleryUtils.bindViewAndTime(handler, tvDate);
    }

    @Override
    public void onItemClick(String url, int num) {
        Intent startImageIntent = new Intent(this, ImageActivity.class);
        ImageHelper.setParameters(startImageIntent, url, num);
        startActivity(startImageIntent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setImagesCount(newConfig);
        // перестроим, чтобы было красиво
        ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanCount(getSpanCount());
    }

    @Override
    protected void onDestroy() {
        requestHelper.cancelAll();
        GalleryViewHolder.cancelRequest();
        super.onDestroy();
    }

    private ImagePreviewLoader.LoaderError makeErrorFn() {
        return new ImagePreviewLoader.LoaderError() {
            @Override
            public void exec(String message) {
                if (message != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ErrorDialog.ERROR_MESSAGE, message);
                    ErrorDialog dialogFragment = new ErrorDialog();
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(getSupportFragmentManager(), "Error Fragment");
                }
            }
        };
    }

    private void setImagesCount(Configuration config) {
        int imagesPerScreen = GalleryUtils.getImagesPerScreen(config, mResources);
        sharedPref.setImagesPerScreen(imagesPerScreen);
    }

    private void initRecyclerView(ImagePreviewLoader.LoaderError onError) {
        recyclerView = findViewById(R.id.galleryView);
        GridLayoutManager imageLayoutManager = new GridLayoutManager(this, getSpanCount());
        recyclerView.setLayoutManager(imageLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(40);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        // should be only 1 picasso object!
        GalleryViewAdapter adapter = new GalleryViewAdapter(requestHelper.getPicasso(this));
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(
            new RecyclerViewListener(imageLayoutManager, adapter, mResources, sharedPref, requestHelper, onError)
        );
    }

    private int getSpanCount() {
        return mResources.getInteger(R.integer.gallerySpanCount);
    }

}
