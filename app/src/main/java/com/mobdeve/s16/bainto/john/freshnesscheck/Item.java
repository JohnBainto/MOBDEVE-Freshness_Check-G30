package com.mobdeve.s16.bainto.john.freshnesscheck;

import java.util.Date;

public class Item {
    private Long id;
    private String name, category;
    private String date;
    private boolean isClicked;

    public Item(Long id, String name, String category, String date) {
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
        return date;
    }

    public void setClicked(boolean isClicked)
    {
        this.isClicked = isClicked;
    }

    public boolean getClicked(){ return isClicked;}

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", isClicked='" + isClicked + '\'' +
                '}';
    }
}