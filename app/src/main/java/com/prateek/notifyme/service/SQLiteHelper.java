package com.prateek.notifyme.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.prateek.notifyme.beans.Notification;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "notification_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "TIMESTAMP";
    private static final String COL3 = "APPNAME";
    private static final String COL4 = "TEXT";
    private static final String COL5 = "PRIORITY";

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL1+ "TEXT PRIMARY KEY, "+
                COL2+ "DATETIME NOT NULL, "+
                COL3+ "TEXT NOT NULL, "+
                COL4+ "TEXT NOT NULL, "+
                COL5+ "TEXT NOT NULL); ";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }



}
