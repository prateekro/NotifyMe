package com.prateek.notifyme.service;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.prateek.notifyme.ListViewItemDTO;
import com.prateek.notifyme.MainActivity;
import com.prateek.notifyme.Priority;
import com.prateek.notifyme.beans.ApplicationBean;
import com.prateek.notifyme.beans.NotificationBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.prateek.notifyme.commons.utils.TAG;

public class NotificationService {

    SQLiteHelper mDatabaseHelper;
    Context mContext;

    public NotificationService(Context applicationContext) {
        this.mContext = applicationContext;
        mDatabaseHelper = new SQLiteHelper(applicationContext);
    }

    //    SQLiteHelper mDatabaseHelper;
//    SQLiteHelper mDatabaseHelper = MainActivity.mDatabaseHelper;
    //on receiving every notification, save target app, notification text, timestamp, priority, unreadCounter
    public void saveNotification(NotificationBean notification, ApplicationBean application){
        Cursor curEnabled = mDatabaseHelper.getEnabledDB(application.getAppName());
        while(curEnabled.moveToNext()){
            if(curEnabled.getString(0).equalsIgnoreCase("false")){
                return;
            }
        }
        //if(!application.isEnabled()) return;
        //fetch the details of the NotificationBean Object
//        String id = notification.getId();

        String appName = notification.getAppName();
        Date time = notification.getTimestamp();
        String text = notification.getText();
        String appId = notification.getAppId();

        //fetch the details of the ApplicationBean Object
       // String priority = application.getPriority();
        String enabled = Boolean.toString(application.isEnabled());
        //String category = application.getCategory();
        //String totalNotifications = Integer.toString(application.getTotalNotifications());
        String unreadNotifications = Integer.toString(application.getUnreadNotifications());
        //String lastNotificationTimestamp = application.getLastNotificationTimestamp();
        //String readTimestamp  = application.getReadTimestamp();
        //String userId = application.getUserId();


        Log.d(TAG, "saveNotification: "+appName +  time + text+ appId );
        //boolean variable for checking the success of events
        boolean isUpdated, isAppInserted, isNotificationSuccess ;
        isNotificationSuccess = mDatabaseHelper.saveNotificationDB(appName, time, text, appId);
        if(mDatabaseHelper.isAppPresent(appId)){
            Cursor cur = mDatabaseHelper.getUnreadNotificationCount(appId);

            while(cur.moveToNext()){

                isUpdated = mDatabaseHelper.updateAppTable(appId,Integer.parseInt(cur.getString(0)));
                if (isUpdated ){
                    Log.d(TAG, "saveNotification: OK #");
                }else{
                    Log.d(TAG, "saveNotification: NOPE #");

                }
            }
        }
        else{

            isAppInserted = mDatabaseHelper.insertApp(appId, appName, enabled,"1", Priority.HIGH.name());
            if (isAppInserted){
                Log.d(TAG, "saveNotification:@@ OK #");
            }else{
                Log.d(TAG, "saveNotification:@@ NOPE #");

            }
        }


        //TODO: add "if not on the app's notification listing page", only then update unread counter
        //check whether app name entry already exists in ApplicationBean DB, if not save it, if yes update and "if not on the app's notification listing page", only then update unread counter

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
            textMap.put(data.getString(0), data.getInt(1));
        }
        return textMap;
    }

    //retrieve all app listings along with their unread counter for dashboard page
    public HashMap<String, Integer> getAllNotifications(){
        Cursor data = mDatabaseHelper.getApplicationListingData();
        HashMap<String, Integer> listData = new HashMap<String, Integer>();
        while (data.moveToNext()) {
            // get all names and put in list
            Log.d(TAG, "getAllNotifications: @@"+data.getString(0)+data.getString(1));
            listData.put(data.getString(0), data.getInt(1));
        }
        return listData;
    }

    //on app tap from dashboard, the unread counter should reset
    public void resetCounter(String appName){

    }

    //delete a particular notification
    public void deleteNotification(String id){
        boolean isDeleted = mDatabaseHelper.deleteNotificationDB(id);
    }

    //delete all notifications of a particular app
    public void clearAllNotifications(String appName){
        boolean isCleared = mDatabaseHelper.clearAllNotificationsDB(appName);
    }

    //delete a particular notification
//    public void deleteNotification(NotificationBean notification){
//
//    }

    public void toggleApplication(String appName){
        Log.d(TAG, "first enter toggleApplication: "+appName);
        Cursor cur = mDatabaseHelper.getEnabledDB(appName);
        Log.d(TAG, "after curr toggleApplication: "+appName);
        while(cur.moveToNext()){
            String isEnabled = cur.getString(0);
            System.out.print(isEnabled);
            Log.d(TAG, "toggleApplication: "+isEnabled);
            if(isEnabled.equalsIgnoreCase("true")){
                isEnabled="false";
            }
            else{
                isEnabled="true";
            }
            Log.d(TAG, "EXIT enter toggleApplication: "+isEnabled);
            boolean success = mDatabaseHelper.toggleUpdateApplicationDB(appName,isEnabled);
        }
    }

    public List<ListViewItemDTO> getEnableStatusOfApps() {
        Cursor cursor = mDatabaseHelper.getEnableStatusForAppsDB();
        List<ListViewItemDTO> result = new ArrayList<>();
        while(cursor.moveToNext()) {
            Log.d(TAG, "getEnableStatusOfApps: "+cursor.getString(1) + cursor.getString(0));
            result.add(mapToDTO(cursor.getString(0), Boolean.valueOf(cursor.getString(1))));
        }
        return result;
    }

    public ListViewItemDTO mapToDTO(String name, Boolean status) {
        return new ListViewItemDTO(status, name);
    }

    public void setAppPriority(String appName, Enum priority){
        mDatabaseHelper.setAppPriorityDB(appName,priority);
    }
}
