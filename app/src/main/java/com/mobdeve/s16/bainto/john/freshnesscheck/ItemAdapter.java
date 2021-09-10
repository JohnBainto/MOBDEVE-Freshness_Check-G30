package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
    private Context context;
    private ArrayList<String> data;
    private ArrayList<String> dataExpiration;
    private ActivityResultLauncher<Intent> myActivityResultLauncher;
    private char type;

    public ItemAdapter(Context context, ArrayList<String> data, char type) {
        this.context = context;
        this.data = data;
        this.type = type;
    }

    public ItemAdapter(ArrayList<String> data, ActivityResultLauncher<Intent> myActivityResultLauncher) {
        this.data = data;
        this.myActivityResultLauncher = myActivityResultLauncher;
    }

    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout, parent, false);

        ItemHolder itemHolder = new ItemHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 'i'){
                    //send to item details activity
                }
                else if(type == 'l'){
                    //send to list activity
                }
            }
        });

        return itemHolder;
    }

    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        if(dataExpiration == null)
            holder.bindData(this.data.get(position));
        else
            holder.bindDataSorted(this.data.get(position), this.dataExpiration.get(position));
    }

    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<String> data){
        this.dataExpiration = null;
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setData(ArrayList<String> data, ArrayList<String> dataExpiration) {
        this.dataExpiration = dataExpiration;
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setType(char type){
        this.type = type;
    }
}