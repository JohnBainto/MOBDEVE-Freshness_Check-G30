package com.mobdeve.s16.bainto.john.freshnesscheck;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    TabLayout mainMenuTab;
    private ArrayList<Item> items;

    /*private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager manager;*/

    private DbHelper dbHelper;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executorService.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O) //limited to android oreo
            @Override
            public void run() {
                dbHelper = DbHelper.getInstance(MainActivity.this);
                items = dbHelper.getAllItemsDefault();
            }
        });

        mainMenuTab = findViewById(R.id.mainMenuTab);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        }

        /*recyclerView = findViewById(R.id.recyclerView);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);*/
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
}