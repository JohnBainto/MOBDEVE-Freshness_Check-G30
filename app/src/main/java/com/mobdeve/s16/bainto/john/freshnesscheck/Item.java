package com.mobdeve.s16.bainto.john.freshnesscheck;

import java.util.Date;

public class Item {
    private long id;
    private String name, category;
    private Date date;
    private boolean isClicked;

    public Item(long id, String name, String category, Date date) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.date = date;
        this.isClicked = false;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date.toString();
    }

    public void setClicked(boolean isClicked)
    {
        this.isClicked = isClicked;
    }
}