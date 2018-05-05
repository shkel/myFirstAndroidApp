package com.example.galleryn.galleryn.disk;

import android.support.annotation.NonNull;

public class Resource {
    private String name;
    private String preview;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    @NonNull
    public String getPath() {
        return path == null ? "": path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
