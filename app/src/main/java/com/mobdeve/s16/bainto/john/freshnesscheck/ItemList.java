package com.mobdeve.s16.bainto.john.freshnesscheck;

import java.util.ArrayList;

public class ItemList {
    private long listId;
    private String listName;
    private String itemId;

    public ItemList(long listId, String listName, String itemId) {
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

    public String getItemId() {
        return itemId;
    }
}
