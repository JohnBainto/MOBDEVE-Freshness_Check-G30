package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public static DbHelper instance = null;

    public DbHelper(Context context) {
        super(context, DbReferences.DATABASE_NAME, null, DbReferences.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbReferences.CREATE_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DbReferences.DROP_TABLE_STATEMENT);
        onCreate(sqLiteDatabase);
    }

    public static synchronized DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }

        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Item> getAllItemsDefault() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_NAME_NAME + " ASC",
                null
        );
        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences._ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_CATEGORY)),
                    LocalDate.parse(c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_LOCAL_DATE)))
            ));
        }
        c.close();
        database.close();

        return items;
    }

    public synchronized void insertItem(Item i) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbReferences.COLUMN_NAME_NAME, i.getName());
        values.put(DbReferences.COLUMN_NAME_CATEGORY, i.getCategory());
        values.put(DbReferences.COLUMN_NAME_LOCAL_DATE, i.getDate());

        database.insert(DbReferences.TABLE_NAME, null, values);

        database.close();
    }

    private final class DbReferences {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "freshness_check.db";

        private static final String TABLE_NAME = "items", _ID = "id", COLUMN_NAME_NAME = "name", COLUMN_NAME_CATEGORY = "category", COLUMN_NAME_LOCAL_DATE = "date";

        private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_NAME + " TEXT, " + COLUMN_NAME_CATEGORY + " TEXT, " + COLUMN_NAME_LOCAL_DATE + " TEXT)";

        private static final String DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}