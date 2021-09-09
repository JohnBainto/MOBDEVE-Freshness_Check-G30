package com.mobdeve.s16.bainto.john.freshnesscheck;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Item {
    private int id;
    private String name, category;
    private LocalDate date;
    private boolean isClicked;

    public Item(int id, String name, String category, LocalDate date) {
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