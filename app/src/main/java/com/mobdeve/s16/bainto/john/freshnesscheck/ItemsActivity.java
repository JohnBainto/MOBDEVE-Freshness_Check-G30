package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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

public class ItemsActivity extends AppCompatActivity {
    private static final String TAG = "ItemsActivity";
    public static final String ITEM_KEY = "ITEM_NAME_KEY";
    public static final String ITEM_KEY_TYPE = "ITEM_NAME_TYPE";
    public static final String DELETED_ID = "DELETED_ID";
    public static final String DELETED_EXPIRY = "DELETED_EXPIRY";
    public static final int DELETE_ITEM_OK = 9876;

    private DbHelper dbHelper;

    private Item item;

    private TextView itemName, itemCategory, itemExpiration;
    private ImageButton editBtn, deleteBtn;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private ActivityResultLauncher<Intent> myActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        item = result.getData().getParcelableExtra(AddItemActivity.EDIT_ITEM_TAG);

                        Log.d(TAG, "Item name: " + item.getName() + "Item Category: " + item.getCategory() + " Item Expiration: " + item.getDate());

                        itemName.setText(item.getName());
                        itemCategory.setText(item.getCategory());
                        itemExpiration.setText(item.getDate());
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        itemName = findViewById(R.id.item_details_name_tv);
        itemCategory = findViewById(R.id.item_details_category_tv);
        itemExpiration = findViewById(R.id.item_details_expiry_date_tv);
        editBtn = findViewById(R.id.item_details_edit_btn);
        deleteBtn = findViewById(R.id.deleteItemIb);

        item = getIntent().getParcelableExtra(ITEM_KEY);

        itemName.setText(item.getName());
        itemCategory.setText(item.getCategory());
        itemExpiration.setText(item.getDate());

        this.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                intent.putExtra(ITEM_KEY, item);
                intent.putExtra(ITEM_KEY_TYPE, 'e');
                myActivityResultLauncher.launch(intent);
            }
        });

        this.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        dbHelper = dbHelper.getInstance(ItemsActivity.this);
                        dbHelper.deleteItemRow(item.getName());
                        Log.d(TAG, "Remaining items: " + getItemNames(dbHelper.getAllItemsDefault()));

                        Intent intent = new Intent();
                        intent.putExtra(DELETED_ID, (int)item.getId());
                        intent.putExtra(DELETED_EXPIRY, item.getDate());
                        setResult(DELETE_ITEM_OK, intent);
                        finish();
                    }
                });
            }
        });
    }

    //for debugging
    public ArrayList<String> getItemNames(ArrayList<Item> data) {
        ArrayList<String> names = new ArrayList<>();

        for(int i = 0; i < data.size(); i++)
            names.add(data.get(i).getName());

        return names;
    }
}
