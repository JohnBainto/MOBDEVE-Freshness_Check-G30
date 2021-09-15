package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

    public static final String ITEM_KEY = "ITEM_NAME_KEY";

    private Intent intent;
    private Item item;

    private TextView itemName, itemCategory, itemExpiration;
    private ImageButton backBtn, editBtn;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private ActivityResultLauncher<Intent> myActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){

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
        backBtn = findViewById(R.id.item_details_back_btn);
        editBtn = findViewById(R.id.item_details_edit_btn);

        item = getIntent().getParcelableExtra(ITEM_KEY);

        itemName.setText(item.getName());
        itemCategory.setText(item.getCategory());
        itemExpiration.setText(item.getDate());

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
}
