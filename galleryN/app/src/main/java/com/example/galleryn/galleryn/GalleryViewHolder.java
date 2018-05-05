package com.example.galleryn.galleryn;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.galleryn.galleryn.utils.GalleryImage;
import com.squareup.picasso.Picasso;


class GalleryViewHolder extends RecyclerView.ViewHolder {
    private static final Object TAG = GalleryViewHolder.class;
    private ImageView imageView;
    private String imageUrl;
    private int imageNumber;

    GalleryViewHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.imagePreview);
    }

    void setListener(final GalleryViewAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            listener.onItemClick(imageUrl, imageNumber);
            }
        });
    }

    void bind(@NonNull GalleryImage image, int number, Picasso picasso) {
        String previewUrl = image.getPreviewUrl();
        if (previewUrl != null)
            picasso.load(previewUrl)
                .tag(TAG)
                .error(android.R.drawable.ic_menu_report_image)
                .placeholder(R.drawable.animated_loading)
                .into(imageView);
        imageUrl = image.getUrl();
        imageNumber = number;
    }

    static void cancelRequest() {
        Picasso.get().cancelTag(TAG);
    }

}