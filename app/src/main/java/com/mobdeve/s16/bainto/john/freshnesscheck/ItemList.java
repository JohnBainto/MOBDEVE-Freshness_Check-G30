package com.mobdeve.s16.bainto.john.freshnesscheck;

public class ItemList {
    private Long listId;
    private String listName;
    private long itemId;

    public ItemList(Long listId, String listName, long itemId) {
        this.listId = listId;
        this.listName = listName;
        this.itemId = itemId;
    }

    public Long getListId() {
        return listId;
    }

    public String getListName() {
        return listName;
    }

    public long getItemId() {
        return itemId;
    }

    @Override
    public String toString() {
        return "ItemList{" +
                "id='" + listId + '\'' +
                ", name='" + listName + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }
}
