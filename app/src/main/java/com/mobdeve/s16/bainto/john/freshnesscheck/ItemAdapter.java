package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
    private static final String TAG = "ItemAdapter";
    private Context context;
    private DbHelper dbHelper;
    private ArrayList<String> data;
    private ArrayList<String> dataExpiration;
    private ActivityResultLauncher<Intent> myActivityResultLauncher;
    private char type;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Item item;

    public ItemAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
        this.type = 'i';
    }

    public ItemAdapter(ArrayList<String> data, ActivityResultLauncher<Intent> myActivityResultLauncher) {
        this.data = data;
        this.myActivityResultLauncher = myActivityResultLauncher;
        this.type = 'i';
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
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            dbHelper = dbHelper.getInstance(context);

                            Log.d(TAG, "Name: " + data.get(itemHolder.getAdapterPosition()) + " Date: " + dataExpiration.get(itemHolder.getAdapterPosition()));
                            item = dbHelper.searchItem(data.get(itemHolder.getAdapterPosition()), dataExpiration.get(itemHolder.getAdapterPosition()));

                            Intent intent = new Intent(parent.getContext(), ItemsActivity.class);
                            intent.putExtra(ItemsActivity.ITEM_KEY, item);
                            Log.d(TAG, "Item Name: " + item.getName() + " Item Category: " + item.getCategory() + " Item Expiration: " + item.getDate());
                            myActivityResultLauncher.launch(intent);
                        }
                    });

                }
                else if(type == 'l'){
                    //send to list activity
                    Intent intent = new Intent(parent.getContext(), listsDetailsActivity.class);
                    intent.putExtra(listsDetailsActivity.LIST_NAME_KEY, data.get(itemHolder.getAdapterPosition()));
                    Log.d(TAG, "onClick: " + intent.getStringExtra(listsDetailsActivity.LIST_NAME_KEY));
                    myActivityResultLauncher.launch(intent);
                }
            }
        });

        return itemHolder;
    }

    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        if(type == 'l'){
            holder.bindData(this.data.get(position));
            holder.setColor(Color.BLACK);
        }
        else {
            try {
                holder.bindDataSorted(this.data.get(position), this.dataExpiration.get(position));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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