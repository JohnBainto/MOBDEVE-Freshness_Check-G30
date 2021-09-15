package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class listsDetailsActivity extends AppCompatActivity {

    public static final String LIST_NAME_KEY = "LIST_NAME_KEY";
    private static final String TAG = "listDetailsActivity";

    private ArrayList<Item> itemData = new ArrayList<Item>();
    private ArrayList<String> itemNames = new ArrayList<String>();
    private RecyclerView recyclerView;
    private TextView listName;
    private ImageButton backBtn, editBtn;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager manager;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private DbHelper myDbHelper;

    private ArrayList<String> data = new ArrayList<String>();

    private ActivityResultLauncher<Intent> myActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        itemNames.addAll(result.getData().getStringArrayListExtra(AddToListActivity.ITEM_NAMES_KEY));
                    }
                    adapter.notifyDataSetChanged();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        this.recyclerView = findViewById(R.id.item_list_rv);
        this.listName = findViewById(R.id.item_list_list_name_tv);
        this.backBtn = findViewById(R.id.item_list_back_btn);
        this.editBtn = findViewById(R.id.item_list_edit_btn);

        Intent intent =  getIntent();
        this.listName.setText(intent.getStringExtra(LIST_NAME_KEY));

        manager = new LinearLayoutManager(listsDetailsActivity.this);
        recyclerView.setLayoutManager(manager);

        adapter = new ItemAdapter(data, myActivityResultLauncher);
        adapter.setData(new ArrayList<>(data), getItemExpirations(itemData));
        recyclerView.setAdapter(adapter);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                myDbHelper = DbHelper.getInstance(listsDetailsActivity.this);
                itemData = myDbHelper.getItemsInList(intent.getStringExtra(LIST_NAME_KEY));
                Log.d(TAG, "" + itemData.size());
                data = getItemNames(itemData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ItemAdapter(data, myActivityResultLauncher);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(adapter);
                                adapter.setData(new ArrayList<>(data), getItemExpirations(itemData));
                            }
                        });
                    }
                });

            }
        });

        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddToListActivity.class);
                myActivityResultLauncher.launch(intent);
            }
        });
    }

    public ArrayList<String> getItemNames(ArrayList<Item> data) {
        ArrayList<String> names = new ArrayList<>();

        for(int i = 0; i < data.size(); i++)
            names.add(data.get(i).getName());

        return names;
    }

    public ArrayList<String> getItemExpirations(ArrayList<Item> data) {
        ArrayList<String> expirations = new ArrayList<>();

        for(int i = 0; i < data.size(); i++)
            expirations.add(data.get(i).getDate());

        return expirations;
    }
}
