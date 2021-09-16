package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void bindDataSorted(String name, String date) throws ParseException {
        this.name.setText(name);
        this.date.setVisibility(View.VISIBLE);
        this.date.setText(date);

        Date expiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        if(expiryDate.before(new Date())){
            this.name.setTextColor(Color.RED);
        }
    }

    public void setColor(int color){
        this.name.setTextColor(color);
    }
}