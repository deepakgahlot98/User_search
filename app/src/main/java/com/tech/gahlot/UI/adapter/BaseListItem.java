package com.tech.gahlot.UI.adapter;

public class BaseListItem {

    private long id;

    public BaseListItem(long id) {
        this.id = id;
        if (id == 0) {
            throw new IllegalArgumentException();
        }
    }

    public long getId() {
        return id;
    }
}

