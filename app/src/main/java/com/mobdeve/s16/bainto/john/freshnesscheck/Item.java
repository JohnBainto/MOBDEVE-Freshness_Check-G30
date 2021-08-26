package com.mobdeve.s16.bainto.john.freshnesscheck;

import java.time.LocalDate;

public class Item {
    private long id;
    private String name, category;
    private LocalDate date;

    public Item(long id, String name, String category, LocalDate date) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }
}