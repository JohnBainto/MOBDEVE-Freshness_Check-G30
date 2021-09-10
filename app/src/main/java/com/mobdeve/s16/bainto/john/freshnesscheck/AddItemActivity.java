package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {
    public static final String
            NEW_ITEM_NAME_KEY = "NEW_ITEM_NAME_KEY",
            NEW_ITEM_CATEGORY_KEY = "NEW_ITEM_CATEGORY_KEY",
            NEW_ITEM_EXPIRATION_KEY = "NEW_ITEM_EXPIRATION_KEY";

    private TextView addItemNameTv, addItemCategoryTv;
    private EditText addItemExpirationEt;
    private ImageButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        this.addItemNameTv = findViewById(R.id.add_item_name_tv);
        this.addItemCategoryTv = findViewById(R.id.add_item_category_tv);
        this.addItemExpirationEt = findViewById(R.id.add_item_date_date);
        this.addBtn = findViewById(R.id.add_item_add_btn);

        this.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addItemNameTv.getText().toString().isEmpty() && !addItemCategoryTv.getText().toString().isEmpty() && !addItemExpirationEt.getText().toString().isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra(NEW_ITEM_NAME_KEY, addItemNameTv.getText().toString());
                    intent.putExtra(NEW_ITEM_CATEGORY_KEY, addItemCategoryTv.getText().toString());
                    intent.putExtra(NEW_ITEM_EXPIRATION_KEY, addItemExpirationEt.getText().toString());

                    setResult(Activity.RESULT_OK, intent);

                    finish();
                }
                else {
                    Toast.makeText(
                            AddItemActivity.this,
                            "Please make sure all entries are filled.",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }
}