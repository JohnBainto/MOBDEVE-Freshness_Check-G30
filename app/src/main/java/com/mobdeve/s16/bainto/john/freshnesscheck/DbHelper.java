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
        sqLiteDatabase.execSQL(DbReferences.CREATE_TABLE_ITEM_STATEMENT);
        sqLiteDatabase.execSQL(DbReferences.CREATE_TABLE_LIST_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DbReferences.DROP_TABLE_ITEM_STATEMENT);
        sqLiteDatabase.execSQL(DbReferences.DROP_TABLE_LIST_STATEMENT);
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
                DbReferences.TABLE_NAME_ITEMS,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_ITEM_NAME + " ASC",
                null
        );
        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                    LocalDate.parse(c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE)))
            ));
        }
        c.close();
        database.close();

        return items;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Item> getAllItemsZtoA() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_ITEMS,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_ITEM_NAME + " DESC",
                null
        );
        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                    LocalDate.parse(c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE)))
            ));
        }
        c.close();
        database.close();

        return items;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Item> getAllItemsCategory() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_ITEMS,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_ITEM_NAME + " DESC",
                null
        );
        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                    LocalDate.parse(c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE)))
            ));
        }
        c.close();
        database.close();

        return items;
    }

    public ArrayList<ItemList> getAllListsDefault() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_LISTS,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_LIST_NAME + " ASC",
                null
        );

        ArrayList<ItemList> lists = new ArrayList<>();
        ArrayList<Long> listItems = new ArrayList<>();
        while(c.moveToNext()) {
            lists.add(new ItemList(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.LIST_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_ITEMS_ID))
            ));
        }
        c.close();
        database.close();

        return lists;
    }

    public synchronized void insertItem(Item i) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbReferences.COLUMN_ITEM_NAME, i.getName());
        values.put(DbReferences.COLUMN_ITEM_CATEGORY, i.getCategory());
        values.put(DbReferences.COLUMN_ITEM_LOCAL_DATE, i.getDate());

        database.insert(DbReferences.TABLE_NAME_ITEMS, null, values);

        database.close();
    }

    public synchronized void insertList(ItemList l) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbReferences.COLUMN_LIST_NAME, l.getListName());
        values.put(DbReferences.COLUMN_ITEM_CATEGORY, l.getUsersId());

        database.insert(DbReferences.TABLE_NAME_ITEMS, null, values);

        database.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Item> searchItemByName(String name) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_ITEMS,
                null,
                "name LIKE ?",
                new String[] {"%"+name+"%"},
                null,
                null,
                DbReferences.COLUMN_ITEM_NAME,
                null);
        ArrayList<Item> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do{
                Item item = new Item(
                        c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                        c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                        c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                        LocalDate.parse(c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE)))
                );

                result.add(item);
            }while (c.moveToNext());
        }

        c.close();

        return result;
    }

    public ArrayList<ItemList> searchListByName(String name) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_LISTS,
                null,
                "name LIKE ?",
                new String[] {"%"+name+"%"},
                null,
                null,
                DbReferences.COLUMN_LIST_NAME,
                null);
        ArrayList<ItemList> result = new ArrayList<>();

        if (c.moveToFirst()) {
            do{
                ItemList list = new ItemList(
                        c.getLong(c.getColumnIndexOrThrow(DbReferences.LIST_ID)),
                        c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_NAME)),
                        c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_ITEMS_ID))
                );

                result.add(list);
            }while (c.moveToNext());
        }

        c.close();

        return result;
    }

    private final class DbReferences {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "freshness_check.db";

        private static final String TABLE_NAME_ITEMS = "items", ITEM_ID = "id", COLUMN_ITEM_NAME = "name", COLUMN_ITEM_CATEGORY = "category", COLUMN_ITEM_LOCAL_DATE = "date";
        private static final String TABLE_NAME_LISTS = "lists", LIST_ID = "list_id", COLUMN_LIST_NAME = "list_name", COLUMN_LIST_ITEMS_ID = "items_id";


        private static final String CREATE_TABLE_ITEM_STATEMENT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ITEMS + " (" + ITEM_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ITEM_NAME + " TEXT, " + COLUMN_ITEM_CATEGORY + " TEXT, " + COLUMN_ITEM_LOCAL_DATE + " TEXT)";
        private static final String CREATE_TABLE_LIST_STATEMENT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_LISTS + " (" + LIST_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_LIST_NAME + " TEXT, " + COLUMN_LIST_ITEMS_ID + " TEXT)";

        private static final String DROP_TABLE_ITEM_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME_ITEMS;
        private static final String DROP_TABLE_LIST_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME_LISTS;
    }
}