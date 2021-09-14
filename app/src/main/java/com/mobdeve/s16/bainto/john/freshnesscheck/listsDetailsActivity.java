package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class listsDetailsActivity extends AppCompatActivity {

    public static final String LIST_NAME_KEY = "LIST_NAME_KEY";
    private static final String TAG = "listDetailsActivity";

    private ArrayList<ItemList> listData = new ArrayList<ItemList>();
    private ArrayList<String> itemNames = new ArrayList<String>();
    private RecyclerView recyclerView;
    private TextView listName;
    private ImageButton backBtn, editBtn, deleteBtn;
    private RecyclerView.Adapter adapter;
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
        this.deleteBtn = findViewById(R.id.item_list_delete_btn);

        Intent intent =  getIntent();
        this.listName.setText(intent.getStringExtra(LIST_NAME_KEY));

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapter = new ItemAdapter(itemNames, myActivityResultLauncher);
        recyclerView.setAdapter(adapter);




        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: edit");
                Intent intent = new Intent(getApplicationContext(), AddListActivity.class);
                intent.putExtra(AddListActivity.TYPE_KEY, 'e');
                intent.putExtra(AddListActivity.NAME_KEY, listName.getText());
                myActivityResultLauncher.launch(intent);
            }
        });

        this.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: delete");
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        myDbHelper = DbHelper.getInstance(listsDetailsActivity.this);
                        myDbHelper.deleteListRow(listName.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new ItemAdapter(listsDetailsActivity.this, data);
                                recyclerView.setAdapter(adapter);
                                setResult(AddListActivity.ADD_LIST_OK, intent);
                                finish();
                            }
                        });
                    }
                });
            }
        });


    }

    @Override
    protected void onStart() {

        super.onStart();
        Intent intent = getIntent();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                myDbHelper = DbHelper.getInstance(listsDetailsActivity.this);
                data = myDbHelper.getItemsInList(intent.getStringExtra(LIST_NAME_KEY));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ItemAdapter(listsDetailsActivity.this, data);
                        recyclerView.setAdapter(adapter);
                    }
                });

            }
        });
    }
}
