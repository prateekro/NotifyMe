package com.prateek.notifyme.service;

import android.database.Cursor;

import com.prateek.notifyme.beans.Notification;
import com.prateek.notifyme.beans.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationService {
    private SQLiteHelper mDatabaseHelper;
    //on receiving every notification, save target app, notification text, timestamp, priority, unreadCounter
    public void saveNotification(Notification notification){
        String id = notification.getId();
        String appName = notification.getAppName();
        Date time = notification.getTimestamp();
        String text = notification.getText();
        String appId = notification.getAppId();

        //TODO: add "if not on the app's notification listing page", only then update unread counter
        //check whether app name entry already exists in Application DB, if not save it, if yes update and "if not on the app's notification listing page", only then update unread counter

    }

    //retrieve all app listings along with their unread counter for dashboard page
    //private Map<Application, unreadCounter> getAppListing()

    //on app tap from dashboard, the unread counter should reset
    public void resetCounter(String appName){

    }

    //get all stored notifications
    public HashMap<String, Integer> getNotifications(){
        Cursor data = mDatabaseHelper.getApplicationListingData();
        HashMap<String, Integer> listData = new HashMap<String, Integer>();
        while (data.moveToNext()) {
            // get all names and put in list
            listData.put(data.getString(2), data.getInt(7));
        }
        return listData;
    }

    //delete all notifications of a particular app
    public void deleteNotification(String appName){

    }

    //delete all notifications
    public void clearAllNotifications(){

    }

    //delete a particular notification
    public void deleteNotification(Notification notification){

    }
}
