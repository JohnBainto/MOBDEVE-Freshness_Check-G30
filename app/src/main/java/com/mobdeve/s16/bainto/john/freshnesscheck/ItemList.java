package com.mobdeve.s16.bainto.john.freshnesscheck;

import java.util.ArrayList;

public class ItemList {
    private long listId;
    private String listName;
    private long itemId;

    public ItemList(long listId, String listName, long itemId) {
        this.listId = listId;
        this.listName = listName;
        this.itemId = itemId;
    }

    public long getListId() {
        return listId;
    }

    public String getListName() {
        return listName;
    }

    public long getItemId() {
        return itemId;
    }
}
