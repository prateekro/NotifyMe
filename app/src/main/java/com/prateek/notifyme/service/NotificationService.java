package com.prateek.notifyme.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.prateek.notifyme.beans.Application;
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
    public void saveNotification(Notification notification, Application application){

        //fetch the details of the Notification Object
        String id = notification.getId();
        String appName = notification.getAppName();
        String time = notification.getTimestamp().toString();
        String text = notification.getText();
        String appId = notification.getAppId();

        //fetch the details of the Application Object
        String priority = application.getPriority();
        String enabled = Boolean.toString(application.isEnabled());
        String category = application.getCategory();
        String totalNotifications = Integer.toString(application.getTotalNotifications());
        String unreadNotifications = Integer.toString(application.getUnreadNotifications());
        String lastNotificationTimestamp = application.getLastNotificationTimestamp();
        String readTimestamp  = application.getReadTimestamp();
        String userId = application.getUserId();


        //boolean variable for checking the success of events
        boolean isUpdated, isAppInserted, isNotificationSuccess ;
        isNotificationSuccess = mDatabaseHelper.saveNotificationDB(id, appName, time, text, appId);
        if(mDatabaseHelper.isAppPresent(appId)){
            Cursor cur = mDatabaseHelper.getUnreadNotificationCount(appId);
            isUpdated = mDatabaseHelper.updateAppTable(appId,Integer.parseInt(cur.getString(0)));
        }
        else{
            isAppInserted = mDatabaseHelper.insertApp(appId, appName, priority, enabled,
                    category,totalNotifications,unreadNotifications,lastNotificationTimestamp,readTimestamp,userId );
        }


        //TODO: add "if not on the app's notification listing page", only then update unread counter
        //check whether app name entry already exists in Application DB, if not save it, if yes update and "if not on the app's notification listing page", only then update unread counter

    }

    public boolean checkExistingApp(String appName) {
        return false;
    }

    //
    public HashMap<String, Integer> getAppNotifications(String appName) {
        HashMap<String, Integer> textMap = new HashMap<String, Integer>();
        Cursor data = mDatabaseHelper.getAppNotifications(appName);
        while (data.moveToNext()) {
            // get all names and put in list
            textMap.put(data.getString(1), data.getInt(2));
        }
        return textMap;
    }

    //retrieve all app listings along with their unread counter for dashboard page
    public HashMap<String, Integer> getAllNotifications(){
        Cursor data = mDatabaseHelper.getApplicationListingData();
        HashMap<String, Integer> listData = new HashMap<String, Integer>();
        while (data.moveToNext()) {
            // get all names and put in list
            listData.put(data.getString(1), data.getInt(2));
        }
        return listData;
    }

    //on app tap from dashboard, the unread counter should reset
    public void resetCounter(String appName){

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
