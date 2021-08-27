package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private TextView itemTv;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemTv = itemView.findViewById(R.id.itemTv);
    }

    public void setItemTv(String itemTv) {
        this.itemTv.setText(itemTv);
    }
}
