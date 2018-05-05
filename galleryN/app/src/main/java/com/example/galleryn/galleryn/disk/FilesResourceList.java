package com.example.galleryn.galleryn.disk;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class FilesResourceList {
    private int limit;
    private int offset;
    private List<Resource> items;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @NonNull
    public List<Resource> getItems() {
        return items == null ? Collections.<Resource>emptyList() : items;
    }

    public void setItems(List<Resource> items) {
        this.items = items;
    }


    @Override
    public String toString() {
        return "FilesResourceList{" +
                "limit=" + limit +
                ", offset=" + offset +
                ", items=" + items +
                '}';
    }
}
