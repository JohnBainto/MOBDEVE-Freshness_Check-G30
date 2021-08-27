package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemHolder extends RecyclerView.ViewHolder {
    private TextView text;

    public ItemHolder(@NonNull View itemView) {
        super(itemView);

        text = itemView.findViewById(R.id.itemTv);
    }

    public void setItemTv(String newName) {
        text.setText(newName);
    }
}
