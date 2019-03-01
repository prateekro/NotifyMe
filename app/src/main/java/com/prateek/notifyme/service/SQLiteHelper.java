package com.prateek.notifyme.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private static final String USER_COL2 = "FNAME";
    private static final String USER_COL3 = "LNAME";
    private static final String USER_COL4 = "DOB";
    private static final String USER_COL5 = "lastLogin";
    private static final String USER_COL6 = "signupTimestamp";

    private static final String APPLICATION_TABLE_NAME = "application_table";
    private static final String APPLICATION_COL1 = "appid";
    private static final String APPLICATION_COL2= "appName";
    private static final String APPLICATION_COL3= "priority";
    private static final String APPLICATION_COL4= "enabled";
    private static final String APPLICATION_COL5= "category";
    private static final String APPLICATION_COL6= "totalNotifications";
    private static final String APPLICATION_COL7= "unreadNotifications";
    private static final String APPLICATION_COL8= "lastNotificationTimestamp";
    private static final String APPLICATION_COL9= "readTimestamp";
    private static final String APPLICATION_COL10= "userId";

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
                USER_COL2+ "TEXT NOT NULL, "+
                USER_COL3+ "TEXT NOT NULL," +
                USER_COL4+ "TEXT NOT NULL," +
                USER_COL5+ "TEXT NOT NULL," +
                USER_COL6+ "TEXT NOT NULL" +"); ";
        db.execSQL(createTable_user);

        String createTable_application ="CREATE TABLE " + APPLICATION_TABLE_NAME+ " (" +
                APPLICATION_COL1+ "TEXT PRIMARY KEY, "+
                APPLICATION_COL2+ "TEXT PRIMARY KEY, "+
                APPLICATION_COL3+ "TEXT PRIMARY KEY, "+
                APPLICATION_COL4+ "TEXT PRIMARY KEY, "+
                APPLICATION_COL5+ "TEXT PRIMARY KEY, "+
                APPLICATION_COL6+ "TEXT PRIMARY KEY, "+
                APPLICATION_COL7+ "TEXT PRIMARY KEY, "+
                APPLICATION_COL8+ "DATETIME PRIMARY KEY, "+
                APPLICATION_COL9+ "DATETIME PRIMARY KEY, "+
                APPLICATION_COL10+ "TEXT NOT NULL" +"); ";
        db.execSQL(createTable_application);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ NOTIFICATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ APPLICATION_TABLE_NAME);
    }

    public Cursor getApplicationListingData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+ APPLICATION_COL2 +", "+ APPLICATION_COL7 +" FROM "+NOTIFICATION_TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAppNotifications(String appName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereVal = "'" + appName + "'";
        String query = "SELECT "+ NOTIFICATION_COL4 +", "+ NOTIFICATION_COL2 +" FROM "+NOTIFICATION_TABLE_NAME + " WHERE "+ NOTIFICATION_COL3+"="+whereVal;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
    