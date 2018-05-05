package com.example.galleryn.galleryn.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class SharedPreferencesHelper {
    private static final String SHARED_PREF_IMG_PER_SCREEN = "SHARED_PREF_IMG_PER_SCREEN";
    private static final String SHARED_PREF_TOTAL_IMG_COUNT = "SHARED_PREF_TOTAL_IMG_COUNT";
    private SharedPreferences mSharedPreferences;

    public SharedPreferencesHelper(@NonNull Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_IMG_PER_SCREEN, Context.MODE_PRIVATE);
    }

    public int getImagesPerScreen() {
        return mSharedPreferences.getInt(SHARED_PREF_IMG_PER_SCREEN, 0);
    }

    public void setImagesPerScreen(int count) {
        mSharedPreferences.edit().putInt(SHARED_PREF_IMG_PER_SCREEN, count).apply();
    }

    public int getImagesCount() {
        return mSharedPreferences.getInt(SHARED_PREF_TOTAL_IMG_COUNT, 0);
    }

    public void setImagesCount(int count) {
        mSharedPreferences.edit().putInt(SHARED_PREF_TOTAL_IMG_COUNT, count).apply();
    }

}
