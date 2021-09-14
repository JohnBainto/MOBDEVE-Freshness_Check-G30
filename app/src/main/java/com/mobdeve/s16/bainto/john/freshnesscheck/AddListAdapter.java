package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddListAdapter extends RecyclerView.Adapter<AddListHolder> {
    private static final String TAG = "AddListAdapter";
    private Context context;
    private ArrayList<Item> items;
    private ActivityResultLauncher<Intent> myActivityResultLauncher;

    public AddListAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    public AddListAdapter(ArrayList<Item> items, ActivityResultLauncher<Intent> myActivityResultLauncher) {
        this.items = items;
        this.myActivityResultLauncher = myActivityResultLauncher;
    }

    public AddListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.add_to_list_recyclerview_layout, parent, false);

        AddListHolder addListHolder = new AddListHolder(view);

        addListHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addListHolder.item.isChecked())
                {
                    items.get(addListHolder.getAdapterPosition()).setClicked(true);
                    Log.d(TAG, "onClick: setclicked");
                }
                else
                {
                    items.get(addListHolder.getAdapterPosition()).setClicked(false);
                }
            }
        });



        return addListHolder;
    }

    public void onBindViewHolder(@NonNull AddListHolder holder, int position) {
        holder.setItem(items.get(position).getName());
        holder.setChecked(items.get(position).getClicked());
    }

    public int getItemCount() {
        return items.size();
    }

    public ArrayList<Item> getItems()
    {
        return items;
    }
}