package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private Long id;
    private String name, category;
    private String date;
    private boolean isClicked;

    public Item() {}
    public Item(Parcel in) {
        id = in.readLong();
        name = in.readString();
        category = in.readString();
        date = in.readString();
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.id);
        out.writeString(this.name);
        out.writeString(this.category);
        out.writeString(this.date);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {

        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[0];
        }
    };
}