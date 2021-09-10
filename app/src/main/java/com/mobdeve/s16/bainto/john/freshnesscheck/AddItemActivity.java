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

public class AddItemActivity extends AppCompatActivity {
    public static final String
            NEW_ITEM_NAME_KEY = "NEW_ITEM_NAME_KEY",
            NEW_ITEM_CATEGORY_KEY = "NEW_ITEM_CATEGORY_KEY",
            NEW_ITEM_EXPIRATION_KEY = "NEW_ITEM_EXPIRATION_KEY",
            ADD_ITEM_TAG = "AddItemActivity";

    private Calendar myCalendar = new GregorianCalendar();

    private TextView addItemNameTv, addItemCategoryTv;
    private EditText addItemExpirationEt;
    private ImageButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        this.addItemNameTv = findViewById(R.id.addItemNameEt);
        this.addItemCategoryTv = findViewById(R.id.addItemCategoryEt);
        this.addItemExpirationEt = findViewById(R.id.addItemDateEt);
        this.addBtn = findViewById(R.id.add_item_add_btn);

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
        String myFormat = "dd/MM/yy"; //put your date format in which you need to display
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        addItemExpirationEt.setText(sdf.format(myCalendar.getTime()));
    }
}