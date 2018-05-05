package com.example.galleryn.galleryn.disk;

import android.support.annotation.NonNull;

public class Link {

    private String href;
    private String method;
    private boolean templated;

    @NonNull
    public String getHref() {
        return href == null ? "" : href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isTemplated() {
        return templated;
    }

    public void setTemplated(boolean templated) {
        this.templated = templated;
    }

    @Override
    public String toString() {
        return "Link{" +
                "href='" + href + '\'' +
                ", method='" + method + '\'' +
                ", templated=" + templated +
                '}';
    }
}
