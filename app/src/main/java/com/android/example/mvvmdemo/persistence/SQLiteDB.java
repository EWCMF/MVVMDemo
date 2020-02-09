package com.android.example.mvvmdemo.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database.db";
    public static final String TABLE_NAME = "string";
    private static final String COL_1 = "id";
    public static final String COL_2 = "currentString";

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Cursor cursor = getAllData();
        if (cursor.getCount() == 0) {
            initDB();
        }
    }
    public void onCreate(SQLiteDatabase db) {
        String stmt = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COL_1 + " INTEGER PRIMARY KEY, " + COL_2 + " TEXT);";
        db.execSQL(stmt);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String stmt = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(stmt);
        onCreate(db);
    }

    private void initDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, "Test");
        db.insert(TABLE_NAME, null, values);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }
}
