package com.mobdeve.s16.bainto.john.freshnesscheck;

public class DbReferences {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "freshness_check.db";

    private static final String TABLE_NAME = "items", _ID = "id", COLUMN_NAME_NAME = "name", COLUMN_NAME_CATEGORY = "category", COLUMN_NAME_LOCAL_DATE = "date";

    private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS" + TABLE_NAME + " (" + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT" + COLUMN_NAME_NAME + " TEXT, " + COLUMN_NAME_CATEGORY + " TEXT, " + COLUMN_NAME_LOCAL_DATE + " TEXT)";

    private static final String DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME;
}