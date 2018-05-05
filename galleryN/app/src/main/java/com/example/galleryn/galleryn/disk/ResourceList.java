package com.example.galleryn.galleryn.disk;

import java.util.List;

public class ResourceList {

    private String sort;
    private String public_key;
    private String path;
    private int limit;
    private int offset;
    private int total;
    private List<Resource> items;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Resource> getItems() {
        return items;
    }

    public void setItems(List<Resource> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ResourceList{" +
                "sort='" + sort + '\'' +
                ", public_key='" + public_key + '\'' +
                ", path='" + path + '\'' +
                ", limit=" + limit +
                ", offset=" + offset +
                ", total=" + total +
                ", items=" + items +
                '}';
    }
}
