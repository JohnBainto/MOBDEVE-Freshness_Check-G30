package com.mobdeve.s16.bainto.john.freshnesscheck;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    TabLayout mainMenuTab;
    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<Item> itemData = new ArrayList<Item>();

    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    private FloatingActionButton addBtn;

    private DbHelper dbHelper;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private ActivityResultLauncher<Intent> newItemResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        dbHelper.insertItem(new Item(
                                null,
                                result.getData().getStringExtra(AddItemActivity.NEW_ITEM_NAME_KEY),
                                result.getData().getStringExtra(AddItemActivity.NEW_ITEM_CATEGORY_KEY),
                                result.getData().getStringExtra(AddItemActivity.NEW_ITEM_EXPIRATION_KEY)
                        ));
                    }
                    else if(result.getResultCode() == Activity.RESULT_CANCELED) {
                        Log.v(TAG, "Result Cancelled.");
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new ItemAdapter(MainActivity.this, data, 'i');
        recyclerView.setAdapter(adapter);

        addBtn = findViewById(R.id.addItemFab);

        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);

        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                dbHelper = DbHelper.getInstance(MainActivity.this);

                /*Item item1 = new Item(null, "bread", "carbs", "21/01/20");
                Item item2 = new Item(null, "apple juice", "liquid", "21/01/25");
                Item item3 = new Item(null, "iced tea", "liquid", "21/01/31");
                dbHelper.insertItem(item1);
                dbHelper.insertItem(item2);
                dbHelper.insertItem(item3);*/

                /*ItemList list1 = new ItemList(null,"breakfast", dbHelper.filterItemsByName("bread").get(0).getId());
                ItemList list2 = new ItemList(null, "drinks", dbHelper.filterItemsByName("apple juice").get(0).getId());
                ItemList list3 = new ItemList(null, "drinks", dbHelper.filterItemsByName("iced tea").get(0).getId());

                dbHelper.insertList(list1);
                dbHelper.insertList(list2);
                dbHelper.insertList(list3);*/


                /*dbHelper.deleteItemRow("bread");
                dbHelper.deleteItemRow("apple juice");
                dbHelper.deleteItemRow("iced tea");*/

                data = getItemNames(dbHelper.getAllItemsDefault());
                itemData = dbHelper.getAllItemsDefault();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ItemAdapter(MainActivity.this, data, 'i');
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });

        mainMenuTab = findViewById(R.id.mainMenuTab);
        mainMenuTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        dbHelper = DbHelper.getInstance(MainActivity.this);

                        if(tab.getPosition() == 0) {
                            data = getItemNames(dbHelper.getAllItemsDefault());
                        }
                        else {
                            data = getListNames(dbHelper.getAllListsDefault());
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setData(new ArrayList<>(data));
                            }
                        });
                    }
                });

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        this.printAllData();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //if (id == R.id.sortName) {

        //}
        //else if (id == R.id.sortExpiration) {

        //}

        return true;
    }

    public ArrayList<String> getItemNames(ArrayList<Item> data) {
        ArrayList<String> names = new ArrayList<>();

        for(int i = 0; i < data.size(); i++)
            names.add(data.get(i).getName());

        return names;
    }

    public ArrayList<String> getListNames(ArrayList<ItemList> data) {
        ArrayList<String> names = new ArrayList<>();

        for(int i = 0; i < data.size(); i++)
            names.add(data.get(i).getListName());

        HashSet<String> uniqueNames = new HashSet<String>(names);

        Collections.sort(names = new ArrayList<String>(uniqueNames));

        return names;
    }

    private void printAllData() {
        Log.d("MainActivity", "printAllData: start");
        for(Item i : itemData) {
            Log.d("MainActivity", "printAllData: " + i.toString());
        }
//        for(ItemList il : listData) {
//            Log.d("MainActivity", "printAllData: " + il.toString());
//        }
    }
}