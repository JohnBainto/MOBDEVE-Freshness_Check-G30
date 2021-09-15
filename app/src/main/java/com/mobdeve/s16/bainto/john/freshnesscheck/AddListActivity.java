package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddListActivity extends AppCompatActivity {
    public static final String TYPE_KEY = "TYPE_KEY", NAME_KEY = "NAME_KEY";
    public static final int ADD_LIST_OK = 1234;
    private static final String TAG = "AddListActivity";

    private ArrayList<Item> items = new ArrayList<Item>();
    private char type;
    private ImageButton back, add;
    private TextView title, listName;
    private RecyclerView recyclerView;
    private AddListAdapter adapter;
    private RecyclerView.LayoutManager manager;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private DbHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        this.back = findViewById(R.id.ilBackBtn);
        this.add = findViewById(R.id.addBtn);
        this.title = findViewById(R.id.addItemListEt);
        this.listName = findViewById(R.id.newListNameEt);
        this.recyclerView = findViewById(R.id.addItemListRV);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new AddListAdapter(AddListActivity.this, items);
        recyclerView.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        type = intent.getCharExtra(TYPE_KEY, 'a');
        if(type == 'e')
        {
            title.setText("Edit Item List");
            back.setVisibility(View.INVISIBLE);
        }


        executorService.execute(new Runnable() {
            @Override
            public void run() {
                myDbHelper = DbHelper.getInstance(AddListActivity.this);
                items = myDbHelper.getAllItemsDefault();

                Intent intent = getIntent();
                if(type == 'e')
                {
                    Log.d(TAG, "run: " + intent.getStringExtra(NAME_KEY));
                    items = myDbHelper.setInList(items, intent.getStringExtra(NAME_KEY));
                    for(Item i : items) {
                        Log.d("MainActivity", "printAllData: " + i.toString());
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new AddListAdapter(AddListActivity.this, items);
                        recyclerView.setAdapter(adapter);
                        Intent intent = getIntent();
                        if(type == 'e')
                        {
                            listName.setText(intent.getStringExtra(NAME_KEY));
                            listName.setEnabled(false);
                        }
                    }
                });

            }
        });
        Log.d(TAG, "onCreate: past executor service");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items = adapter.getItems();
                if(type == 'e')
                {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            myDbHelper.deleteListRow(listName.getText().toString());
                        }
                    });
                }
                for(Item i : items) {
                    if(i.getClicked())
                    {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "item = " + i.getName());
                                ItemList list = new ItemList(null, listName.getText().toString(), i.getId());
                                Log.d(TAG, list.toString());
                                myDbHelper.insertList(list);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent();
                                        intent.putExtra(NAME_KEY, listName.getText().toString());
                                        setResult(ADD_LIST_OK, intent);
                                        finish();
                                    }
                                });
                            }
                        });
                    }
                }


            }
        });
    }

}
