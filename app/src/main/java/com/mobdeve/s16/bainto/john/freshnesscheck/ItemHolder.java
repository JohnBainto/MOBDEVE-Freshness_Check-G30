package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemHolder extends RecyclerView.ViewHolder {
    private TextView name, date;

    public ItemHolder(@NonNull View itemView) {
        super(itemView);

        this.name = itemView.findViewById(R.id.itemTv);
        this.date = itemView.findViewById(R.id.dateTv);
    }

    public void bindData(String s) {
        this.name.setText(s);
        this.date.setVisibility(View.GONE);
    }

    public void bindDataSorted(String name, String date) {
        this.name.setText(name);
        this.date.setVisibility(View.VISIBLE);
        this.date.setText(date);
    }
}