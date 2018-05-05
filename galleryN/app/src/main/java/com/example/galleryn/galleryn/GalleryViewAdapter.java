package com.example.galleryn.galleryn;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.galleryn.galleryn.utils.GalleryImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewHolder> {

    private OnItemClickListener mListener;
    private List<GalleryImage> images = new ArrayList<>();
    private Picasso picasso;

    GalleryViewAdapter(@NonNull Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    @NonNull
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.gallery_preview, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        if (position >= 0 && position < images.size()) {
            GalleryImage image = images.get(position);
            holder.bind(image, position + 1, picasso);
            holder.setListener(mListener);
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public interface OnItemClickListener{
        void onItemClick(String url, int num);
    }

    void setListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void addImageInfo(@NonNull GalleryImage image) {
        images.add(image);
    }
}
