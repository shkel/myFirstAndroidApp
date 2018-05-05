package com.example.galleryn.galleryn;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.galleryn.galleryn.utils.ImageHelper;
import com.example.galleryn.galleryn.utils.SharedPreferencesHelper;
import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {
    private static final Object TAG = ImageActivity.class;

    private final Picasso picasso = Picasso.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initViews();
    }

    @Override
    protected void onDestroy() {
        picasso.cancelTag(TAG);
        super.onDestroy();
    }

    private void initViews() {
        findViewById(R.id.ibBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageHelper imageHelper = new ImageHelper(getIntent().getExtras());
        setHeader(imageHelper);
        loadAndSetImage(imageHelper);
    }

    private void setHeader(@NonNull ImageHelper imageHelper) {
        String info = getImageInfo(imageHelper);
        ((TextView) findViewById(R.id.imageHeader)).setText(info);
    }

    private void loadAndSetImage(@NonNull ImageHelper imageHelper) {
        String imageUrl = imageHelper.getUrl();
        if (imageUrl != null)
            picasso.load(imageUrl)
                    .tag(TAG)
                    .error(android.R.drawable.ic_menu_report_image)
                    .placeholder(R.drawable.animated_loading)
                    .into((ImageView) findViewById(R.id.imageFullScreen));
    }

    private String getImageInfo(@NonNull ImageHelper imageHelper) {
        return imageHelper.getNumber()
                + getResources().getString(R.string.of)
                + new SharedPreferencesHelper(this).getImagesCount();
    }
}
