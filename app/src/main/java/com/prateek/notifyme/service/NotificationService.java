package com.prateek.notifyme.service;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prateek.notifyme.MainActivity;
import com.prateek.notifyme.beans.ApplicationBean;
import com.prateek.notifyme.beans.NotificationBean;

import java.util.Date;
import java.util.HashMap;

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

            isAppInserted = mDatabaseHelper.insertApp(appId, appName, enabled,
                    "1");
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
    public void clearAllNotifications(String appId){
        boolean isCleared = mDatabaseHelper.clearAllNotificationsDB(appId);
    }

    //delete a particular notification
//    public void deleteNotification(NotificationBean notification){
//
//    }

    public void toggleApplication(String appName){
        Cursor cur = mDatabaseHelper.getEnabledDB(appName);
        while(cur.moveToNext()){
            String isEnabled = cur.getString(0);
            if(isEnabled.equalsIgnoreCase("true")){
                isEnabled="false";
            }
            else{
                isEnabled="true";
            }
            boolean success = mDatabaseHelper.toggleUpdateApplicationDB(appName,isEnabled);
        }
    }

    public void archiveNotification() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("notifications");
//        myRef.setValue("Hello, World!");
//        myRef.setValue("Check instance");
        myRef.child("user1").child("txt").setValue("text 2");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                System.out.println("Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value."+ error.toException());
            }
        });
    }

}
