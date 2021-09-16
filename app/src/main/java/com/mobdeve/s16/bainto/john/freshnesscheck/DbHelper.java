package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = "DbHelper";
    public static DbHelper instance = null;

    public DbHelper(Context context) {
        super(context, DbReferences.DATABASE_NAME,null, DbReferences.DATABASE_VERSION);
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

    public ArrayList<Item> getAllItemsDefault() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_ITEMS,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_ITEM_NAME + " COLLATE NOCASE ASC",
                null
        );
        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE))
            ));
        }
        c.close();
//        database.close();

        return items;
    }

    public ArrayList<Item> getAllItemsDescName() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_ITEMS,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_ITEM_NAME + " COLLATE NOCASE DESC",
                null
        );
        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE))
            ));
        }
        c.close();
//        database.close();

        return items;
    }

    public ArrayList<Item> getAllItemsAscExpiration() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_ITEMS,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_ITEM_LOCAL_DATE + " ASC",
                null
        );
        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE))
            ));
        }
        c.close();
//        database.close();

        return items;
    }

    public ArrayList<Item> getAllItemsDescExpiration() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_ITEMS,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_ITEM_LOCAL_DATE + " DESC",
                null
        );
        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE))
            ));
        }
        c.close();
//        database.close();

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
                DbReferences.COLUMN_LIST_NAME + " COLLATE NOCASE ASC",
                null
        );

        ArrayList<ItemList> lists = new ArrayList<>();
        while(c.moveToNext()) {
            lists.add(new ItemList(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.LIST_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_NAME)),
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_ITEMS_ID))
            ));
        }
        c.close();
//        database.close();

        return lists;
    }

    public ArrayList<ItemList> getAllListsDescName() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_LISTS,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_LIST_NAME + " COLLATE NOCASE DESC",
                null
        );

        ArrayList<ItemList> lists = new ArrayList<>();
        while(c.moveToNext()) {
            lists.add(new ItemList(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.LIST_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_NAME)),
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_ITEMS_ID))
            ));
        }
        c.close();
//        database.close();

        return lists;
    }

    public synchronized void insertItem(Item i) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbReferences.COLUMN_ITEM_NAME, i.getName());
        values.put(DbReferences.COLUMN_ITEM_CATEGORY, i.getCategory());
        values.put(DbReferences.COLUMN_ITEM_LOCAL_DATE, i.getDate());

        database.insert(DbReferences.TABLE_NAME_ITEMS, null, values);

//        database.close();
    }

    public synchronized void insertList(ItemList l) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbReferences.COLUMN_LIST_NAME, l.getListName());
        values.put(DbReferences.COLUMN_LIST_ITEMS_ID, l.getItemId());

        database.insert(DbReferences.TABLE_NAME_LISTS, null, values);

//        database.close();
    }

    public ArrayList<Item> filterItemsByName(String name) {
        SQLiteDatabase database = this.getReadableDatabase();

        String queryString = "SELECT * FROM " + DbReferences.TABLE_NAME_ITEMS + " WHERE " + DbReferences.COLUMN_ITEM_NAME + " = ? ";
        Cursor c = database.rawQuery(queryString, new String[] {name});

        ArrayList<Item> items = new ArrayList<>();
        if (c.moveToFirst()) {
            do{
                Item item = new Item(
                        c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                        c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                        c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                        c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE))
                );

                items.add(item);
            }while (c.moveToNext());
        }

        c.close();

        return items;
    }

    public ArrayList<Item> filterItemsByCategory(String category) {
        SQLiteDatabase database = this.getReadableDatabase();

        String queryString = "SELECT * FROM " + DbReferences.TABLE_NAME_ITEMS + " WHERE " + DbReferences.COLUMN_ITEM_CATEGORY + " = ? ";
        Cursor c = database.rawQuery(queryString, new String[] {category});

        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE))
            ));
        }
        c.close();
//        database.close();

        return items;
    }

    public ArrayList<Item> filterItemsByExpiration(String expiration) {
        SQLiteDatabase database = this.getReadableDatabase();

        String queryString = "SELECT * FROM " + DbReferences.TABLE_NAME_ITEMS + " WHERE " + DbReferences.COLUMN_ITEM_LOCAL_DATE + " = ? ";

        Cursor c = database.rawQuery(queryString, new String[] {expiration});

        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE))
            ));
        }
        c.close();
//        database.close();

        return items;
    }

    public Item searchItem(String name, String expiration) {
        SQLiteDatabase database = this.getReadableDatabase();
        Item item = new Item();

        Cursor c = database.query(
                DbReferences.TABLE_NAME_ITEMS,
                null,
                DbReferences.COLUMN_ITEM_NAME + "=?" + " AND " + DbReferences.COLUMN_ITEM_LOCAL_DATE + "=?",
                new String[] {name, expiration},
                null,
                null,
                null,
            null);

        while(c.moveToNext()) {
            item = new Item(c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE)));
        }


        c.close();
//        database.close();

        return item;
    }

    public void updateItemName(String newName, long id, String oldName) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + DbReferences.TABLE_NAME_ITEMS + " SET " + DbReferences.COLUMN_ITEM_NAME + " = '" + newName + "' WHERE " + DbReferences.ITEM_ID + " = '" + id + "'"
                + " AND " + DbReferences.COLUMN_ITEM_NAME + " = '" + oldName + "'";

        database.execSQL(query);
    }

    public void updateItemCategory(String newCategory, long id, String oldCategory) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + DbReferences.TABLE_NAME_ITEMS + " SET " + DbReferences.COLUMN_ITEM_CATEGORY + " = '" + newCategory + "' WHERE " + DbReferences.ITEM_ID + " = '" + id + "'"
                + " AND " + DbReferences.COLUMN_ITEM_CATEGORY + " = '" + oldCategory + "'";

        database.execSQL(query);
    }

    public void updateItemExpiration(String newDate, long id, String oldDate) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + DbReferences.TABLE_NAME_ITEMS + " SET " + DbReferences.COLUMN_ITEM_LOCAL_DATE + " = '" + newDate + "' WHERE " + DbReferences.ITEM_ID + " = '" + id + "'"
                + " AND " + DbReferences.COLUMN_ITEM_LOCAL_DATE + " = '" + oldDate + "'";

        database.execSQL(query);
    }

    public void deleteItemRow(String name) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(DbReferences.TABLE_NAME_ITEMS, DbReferences.COLUMN_ITEM_NAME + "=?", new String[]{name});

//        database.close();
    }

    public void  deleteListRow(String name) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(DbReferences.TABLE_NAME_LISTS,DbReferences.COLUMN_LIST_NAME + "=?", new String[] {name});

//        database.close();
    }

    //method to set if the item is already in the list
    public ArrayList<Item> setInList(ArrayList<Item> items, String listName){
        SQLiteDatabase database = this.getReadableDatabase();
        Log.d(TAG, "setInList: listname = " + listName);

        Cursor c = database.query(
                DbReferences.TABLE_NAME_LISTS,
                null,
                DbReferences.COLUMN_LIST_NAME + " = ?",
                new String[]{listName},
                null,
                null,
                null,
                null
        );

        while(c.moveToNext())
        {
            for(Item i : items) {
                Log.d(TAG, "setInList: c.id = " + c.getLong(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_ITEMS_ID)));
                Log.d(TAG, "setInList: i.id = " + i.getId());
                if(i.getId() == c.getLong(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_ITEMS_ID)))
                {
                    Log.d(TAG, "setInList: " + i.getName() + " got clicked");
                    i.setClicked(true);
                    break;
                }
            }
        }

        c.close();
//        database.close();

        return items;
    }

    public ArrayList<Item> getItemsInList(String listName){
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Item> items = new ArrayList<>();
        ArrayList<Long> id = new ArrayList<>();

        Cursor c = database.query(
                DbReferences.TABLE_NAME_LISTS,
                null,
                DbReferences.COLUMN_LIST_NAME + " = ?",
                new String[]{listName},
                null,
                null,
                null,
                null
        );

        while(c.moveToNext())
        {
            id.add(c.getLong(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_ITEMS_ID)));
        }

        c.close();

        for(Long i : id) {
            c = database.query(
                    DbReferences.TABLE_NAME_ITEMS,
                    null,
                    DbReferences.ITEM_ID + " = ?",
                    new String[]{i.toString()},
                    null,
                    null,
                    null,
                    null
            );
            while(c.moveToNext())
            {
                items.add(new Item(
                        i,
                        c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                        c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                        c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE))
                ));
            }
        }

        c.close();

        Collections.sort(items, Item.COMPARE_BY_EXPIRATION);

        return items;
    }

    public int getMostRecentId()
    {
        int id;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor c = database.query(
                DbReferences.TABLE_NAME_ITEMS,
                new String[]{DbReferences.ITEM_ID},
                null,
                null,
                null,
                null,
                DbReferences.ITEM_ID + " DESC",
                "1"
        );

        c.moveToNext();
        id = c.getInt(c.getColumnIndexOrThrow(DbReferences.ITEM_ID));

        c.close();
//        database.close();

        return id;
    }

    private final class DbReferences {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "freshness_check.db";

        private static final String TABLE_NAME_ITEMS = "items", ITEM_ID = "id", COLUMN_ITEM_NAME = "name", COLUMN_ITEM_CATEGORY = "category", COLUMN_ITEM_LOCAL_DATE = "expiration_date";
        private static final String TABLE_NAME_LISTS = "lists", LIST_ID = "list_id", COLUMN_LIST_NAME = "list_name", COLUMN_LIST_ITEMS_ID = "items_id";


        private static final String CREATE_TABLE_ITEM_STATEMENT = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME_ITEMS + " (" + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_ITEM_CATEGORY + " TEXT, " +
                COLUMN_ITEM_LOCAL_DATE + " TEXT)";
        private static final String CREATE_TABLE_LIST_STATEMENT = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME_LISTS + " (" + LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_ITEMS_ID + " INTEGER, FOREIGN KEY(" + COLUMN_LIST_ITEMS_ID + ") REFERENCES " +
                TABLE_NAME_ITEMS + "(" + ITEM_ID + "))";

        private static final String DROP_TABLE_ITEM_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME_ITEMS;
        private static final String DROP_TABLE_LIST_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME_LISTS;
    }
}