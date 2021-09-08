package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
    private ArrayList<String> data;

    public ItemAdapter(ArrayList<String> data) {
        this.data = data;
    }

    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout, parent, false);

        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.setItemTv(data.get(position));
    }

    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<String> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}