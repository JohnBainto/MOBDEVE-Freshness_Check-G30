package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddListHolder extends RecyclerView.ViewHolder {
    public CheckBox item;

    public AddListHolder(@NonNull View itemView) {
        super(itemView);

        this.item = itemView.findViewById(R.id.add_to_list_layout_cb);
    }



    public void setItem(String itemName){
        item.setText(itemName);
    }
}