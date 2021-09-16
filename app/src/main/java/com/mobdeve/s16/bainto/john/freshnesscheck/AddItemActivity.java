package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddItemActivity extends AppCompatActivity {
    public static final String
            NEW_ITEM_NAME_KEY = "NEW_ITEM_NAME_KEY",
            NEW_ITEM_CATEGORY_KEY = "NEW_ITEM_CATEGORY_KEY",
            NEW_ITEM_EXPIRATION_KEY = "NEW_ITEM_EXPIRATION_KEY",
            EDIT_ITEM_TAG = "EDIT_ITEM",
            NEW_ITEM_YEAR_KEY = "NEW+ITEM_YEAR_KEY",
            NEW_ITEM_MONTH_KEY = "NEW+NEW_ITEM_MONTH_KEY",
            NEW_ITEM_DAY_KEY = "NEW+NEW_ITEM_DAY_KEY";

    private Calendar myCalendar = new GregorianCalendar();
    private char type;
    private DbHelper dbHelper;
    private TextView addItemNameTv, addItemCategoryTv;
    private EditText addItemExpirationEt;
    private ImageButton addBtn;
    private Intent intent;
    private Item item;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addItemNameTv = findViewById(R.id.addItemNameEt);
        addItemCategoryTv = findViewById(R.id.addItemCategoryEt);
        addItemExpirationEt = findViewById(R.id.addItemDateEt);
        addBtn = findViewById(R.id.add_item_add_btn);

        intent = getIntent();
        type = intent.getCharExtra(ItemsActivity.ITEM_KEY_TYPE, 'a');

        if(type == 'e') {
            item = intent.getParcelableExtra(ItemsActivity.ITEM_KEY);

            addItemNameTv.setText(item.getName());
            addItemCategoryTv.setText(item.getCategory());
            addItemExpirationEt.setText(item.getDate());
        }

        addItemExpirationEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddItemActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        this.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addItemNameTv.getText().toString().isEmpty() && !addItemCategoryTv.getText().toString().isEmpty() && !addItemExpirationEt.getText().toString().isEmpty() && type == 'a') {
                    Intent intent = new Intent();
                    intent.putExtra(NEW_ITEM_NAME_KEY, addItemNameTv.getText().toString());
                    intent.putExtra(NEW_ITEM_CATEGORY_KEY, addItemCategoryTv.getText().toString());
                    intent.putExtra(NEW_ITEM_EXPIRATION_KEY, addItemExpirationEt.getText().toString());
                    intent.putExtra(NEW_ITEM_YEAR_KEY, myCalendar.get(Calendar.YEAR));
                    intent.putExtra(NEW_ITEM_MONTH_KEY, myCalendar.get(Calendar.MONTH));
                    intent.putExtra(NEW_ITEM_DAY_KEY, myCalendar.get(Calendar.DAY_OF_MONTH));

                    setResult(Activity.RESULT_OK, intent);

                    finish();
                }
                else if(!addItemNameTv.getText().toString().isEmpty() && !addItemCategoryTv.getText().toString().isEmpty() && !addItemExpirationEt.getText().toString().isEmpty() && type == 'e') {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            dbHelper = dbHelper.getInstance(AddItemActivity.this);

                            dbHelper.updateItemName(addItemNameTv.getText().toString(), item.getId(), item.getName());
                            dbHelper.updateItemCategory(addItemCategoryTv.getText().toString(), item.getId(), item.getName());
                            dbHelper.updateItemExpiration(addItemExpirationEt.getText().toString(), item.getId(), item.getName());

                            item.setName(addItemNameTv.getText().toString());
                            item.setCategory(addItemCategoryTv.getText().toString());
                            item.setDate(addItemExpirationEt.getText().toString());

                            Intent intent = new Intent();
                            intent.putExtra(EDIT_ITEM_TAG, item);
                            setResult(Activity.RESULT_OK, intent);

                            finish();
                        }
                    });
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

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };

    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        addItemExpirationEt.setText(sdf.format(myCalendar.getTime()));
    }
}