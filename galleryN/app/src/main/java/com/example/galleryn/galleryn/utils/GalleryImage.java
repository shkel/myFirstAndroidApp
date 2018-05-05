package com.example.galleryn.galleryn.utils;

import android.support.annotation.Nullable;

public class GalleryImage {
    private String info;
    private String url;
    @Nullable
    private String previewUrl;

    public GalleryImage(String info, String url, @Nullable String previewUrl) {
        this.info = info;
        this.url = url;
        this.previewUrl = previewUrl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUrl() {
        return url;
    }

    public String getPreviewUrl() {
        return previewUrl == null ? url : previewUrl;
    }
}
