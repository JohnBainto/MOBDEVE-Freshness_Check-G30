package com.mobdeve.s16.bainto.john.freshnesscheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {
    public static DbHelper instance = null;
    private static final DateFormat dateFormat = new SimpleDateFormat("MM:dd:yyyy");

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

    public ArrayList<Item> getAllItemsDefault() throws ParseException {
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
                    dateFormat.parse(c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE)))
            ));
        }
        c.close();
        database.close();

        return items;
    }

    public ArrayList<Item> getAllItemsDescName() throws ParseException {
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
                    dateFormat.parse(c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE)))
            ));
        }
        c.close();
        database.close();

        return items;
    }

    public ArrayList<Item> getAllItemsAscCategory() throws ParseException {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_ITEMS,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_ITEM_CATEGORY + " ASC",
                null
        );
        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                    dateFormat.parse(c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE)))
            ));
        }
        c.close();
        database.close();

        return items;
    }

    public ArrayList<Item> getAllItemsDescCategory() throws ParseException {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_ITEMS,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_ITEM_CATEGORY + " DESC",
                null
        );
        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.ITEM_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_CATEGORY)),
                    dateFormat.parse(c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE)))
            ));
        }
        c.close();
        database.close();

        return items;
    }

    public ArrayList<Item> getAllItemsAscExpiration() throws ParseException {
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
                    dateFormat.parse(c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE)))
            ));
        }
        c.close();
        database.close();

        return items;
    }

    public ArrayList<Item> getAllItemsDescExpiration() throws ParseException {
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
                    dateFormat.parse(c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE)))
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
        while(c.moveToNext()) {
            lists.add(new ItemList(
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.LIST_ID)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_NAME)),
                    c.getLong(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_ITEMS_ID))
            ));
        }
        c.close();
        database.close();

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
                DbReferences.COLUMN_LIST_NAME + " DESC",
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
        values.put(DbReferences.COLUMN_ITEM_CATEGORY, l.getItemId());

        database.insert(DbReferences.TABLE_NAME_ITEMS, null, values);

        database.close();
    }

    public ArrayList<Item> searchItemByName(String name) throws ParseException {
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
                        dateFormat.parse(c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_ITEM_LOCAL_DATE)))
                );

                result.add(item);
            }while (c.moveToNext());
        }

        c.close();

        return result;
    }

    public ArrayList<ItemList> getFilterItemsCategory(String category) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_LISTS,
                null,
                "category = ?",
                new String[] {"%"+category+"%"},
                null,
                null,
                DbReferences.COLUMN_LIST_NAME + " ASC",
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
        database.close();

        return lists;
    }

    public ArrayList<ItemList> getFilterItemsExpiration(Date expiration) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(
                DbReferences.TABLE_NAME_LISTS,
                null,
                "expiration_date = ?",
                new String[] {"%"+expiration+"%"},
                null,
                null,
                DbReferences.COLUMN_LIST_NAME + " ASC",
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
        database.close();

        return lists;
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
                        c.getLong(c.getColumnIndexOrThrow(DbReferences.COLUMN_LIST_ITEMS_ID))
                );

                result.add(list);
            }while (c.moveToNext());
        }

        c.close();

        return result;
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

    public void updateListName(String newName, long id, String oldName) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + DbReferences.TABLE_NAME_LISTS + " SET " + DbReferences.COLUMN_LIST_NAME + " = '" + newName + "' WHERE " + DbReferences.LIST_ID + " = '" + id + "'"
                + " AND " + DbReferences.COLUMN_LIST_NAME + " = '" + oldName + "'";

        database.execSQL(query);
    }

    public void updateListItems(String newItems, long id, String oldItems) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + DbReferences.TABLE_NAME_LISTS + " SET " + DbReferences.COLUMN_LIST_ITEMS_ID + " = '" + newItems + "' WHERE " + DbReferences.LIST_ID + " = '" + id + "'"
                + " AND " + DbReferences.COLUMN_LIST_ITEMS_ID + " = '" + oldItems + "'";

        database.execSQL(query);
    }

    public boolean deleteItemRow(String name) {
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(DbReferences.DATABASE_NAME, DbReferences.TABLE_NAME_ITEMS + "=" + name, null) > 0;
    }

    public boolean deleteListRow(String name) {
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(DbReferences.DATABASE_NAME, DbReferences.TABLE_NAME_LISTS + "=" + name, null) > 0;
    }

    public ArrayList<Item> setInList(ArrayList<Item> items){
        for(Item i : items) {
            //query itemlist for item i in db
        }

        return items;
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
                COLUMN_ITEM_LOCAL_DATE + " DATE)";
        private static final String CREATE_TABLE_LIST_STATEMENT = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME_LISTS + " (" + LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_ITEMS_ID + " INTEGER, FOREIGN KEY(" + COLUMN_LIST_ITEMS_ID + ") REFERENCES " +
                TABLE_NAME_ITEMS + "(" + ITEM_ID + "))";

        private static final String DROP_TABLE_ITEM_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME_ITEMS;
        private static final String DROP_TABLE_LIST_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME_LISTS;
    }
}