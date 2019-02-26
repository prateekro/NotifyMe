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
    private static final String NOTIFICATION_TABLE_NAME = "notification_table";
    private static final String NOTIFICATION_COL1 = "ID";
    private static final String NOTIFICATION_COL2 = "TIMESTAMP";
    private static final String NOTIFICATION_COL3 = "APPNAME";
    private static final String NOTIFICATION_COL4 = "TEXT";
    private static final String NOTIFICATION_COL5 = "PRIORITY";

    private static final String USER_TABLE_NAME = "user_table";
    private static final String USER_COL1 = "EMAIL";
    private static final String USER_COL2 = "NAME";
    private static final String USER_COL3 = "PASSWORD";

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable_notification = "CREATE TABLE " + NOTIFICATION_TABLE_NAME + " (" +
                NOTIFICATION_COL1+ "TEXT PRIMARY KEY, "+
                NOTIFICATION_COL2+ "DATETIME NOT NULL, "+
                NOTIFICATION_COL3+ "TEXT NOT NULL, "+
                NOTIFICATION_COL4+ "TEXT NOT NULL, "+
                NOTIFICATION_COL5+ "TEXT NOT NULL); ";
        db.execSQL(createTable_notification);

        String createTable_user = "CREATE TABLE " + USER_TABLE_NAME+ " (" +
                USER_COL1+ "TEXT PRIMARY KEY, "+
                USER_COL2+ "DATETIME NOT NULL, "+
                USER_COL3+ "TEXT NOT NULL); ";
        db.execSQL(createTable_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ NOTIFICATION_TABLE_NAME);
    }



}
    