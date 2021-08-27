package com.mobdeve.s16.bainto.john.freshnesscheck;

import java.util.ArrayList;

public class ItemList {
    private long listId;
    private String listName;
    private String usersId;

    public ItemList(long listId, String listName, String usersId) {
        this.listId = listId;
        this.listName = listName;
        this.usersId = usersId;
    }

    public long getListId() {
        return listId;
    }

    public String getListName() {
        return listName;
    }

    public String getUsersId() {
        return usersId;
    }
}
