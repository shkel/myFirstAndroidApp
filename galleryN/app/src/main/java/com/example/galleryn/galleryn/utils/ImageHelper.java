package com.example.galleryn.galleryn.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ImageHelper {
    private static final String URL = "ImageUrl";
    private static final String NUMBER = "ImageNumber";

    private Bundle bundle;

    public ImageHelper(Bundle bundle) {
        this.bundle = bundle;
    }

    @Nullable
    public String getUrl() {
        return bundle != null ? bundle.getString(URL) : null;
    }

    public String getNumber() {
        int num = bundle.getInt(NUMBER, -1);
        return bundle != null && num > -1 ? Integer.toString(num) : "";
    }

    public static void setParameters(@NonNull Intent intent, String url, int num) {
        intent.putExtra(URL, url);
        intent.putExtra(NUMBER, num);
    }
}
