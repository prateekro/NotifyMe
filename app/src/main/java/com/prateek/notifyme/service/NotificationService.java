package com.prateek.notifyme.service;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.prateek.notifyme.ListViewItemDTO;
import com.prateek.notifyme.Priority;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prateek.notifyme.MainActivity;
import com.prateek.notifyme.ListViewItemDTO;
import com.prateek.notifyme.Priority;
import com.prateek.notifyme.beans.ApplicationBean;
import com.prateek.notifyme.beans.NotificationBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.prateek.notifyme.commons.utils.TAG;

public class NotificationService {

    private static SQLiteHelper mDatabaseHelper;
    Context mContext;
    static DatabaseReference firebaseNotificationReference;

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
                // isUpdated = mDatabaseHelper.updateAppTable(appName,Integer.parseInt(cur.getString(0)));
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
    public HashMap<String, ArrayList<String>> getAppNotifications(String appName) {
        HashMap<String, ArrayList<String>> textMap = new HashMap<String, ArrayList<String>>();
        Cursor data = mDatabaseHelper.getAppNotifications(appName);
        while (data.moveToNext()) {
            Log.d(TAG, "getAppNotifications CHECK: "+data.getString(0) + data.getInt(1));
            // get all names and put in list
            ArrayList <String> dataX = new ArrayList<String>();
            String txt = data.getString(1);
            String timeStamp = data.getString(2);
            String pkg = data.getString(3);
            dataX.add(txt);
            dataX.add(timeStamp);
            dataX.add(pkg);
            textMap.put(data.getString(0), dataX);
        }
        return textMap;
    }

    //retrieve all app listings along with their unread counter for dashboard page
    public HashMap<String, ArrayList<String>> getAllNotifications(){
        Cursor data = mDatabaseHelper.getApplicationListingData();
        HashMap<String, ArrayList<String>> listData = new HashMap<String, ArrayList<String>>();
        while (data.moveToNext()) {
            // get all names and put in list
            Log.d(TAG, "getAllNotifications: @@"+data.getString(0)+data.getString(1));
            ArrayList <String> dataX = new ArrayList<String>();
            String app_nm = String.valueOf(data.getInt(1));
            String packg = data.getString(2);
            String priority = data.getString(3);
            dataX.add(app_nm);
            dataX.add(packg);
            dataX.add(priority);
            listData.put(data.getString(0), dataX); //appname unread package
        }
        return listData;
    }

    //on app tap from dashboard, the unread counter should reset
    public void resetCounter(String appName){
        mDatabaseHelper.resetApp(appName);
    }

    //delete a particular notification
    public static void deleteNotification(String id){
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

    public void toggleApplication(String appName, boolean status){
        Log.d(TAG, "first enter toggleApplication: "+appName);
        Cursor cur = mDatabaseHelper.getEnabledDB(appName);
        Log.d(TAG, "after curr toggleApplication: "+appName);
        while(cur.moveToNext()){
            String isEnabled = cur.getString(0);

            if(isEnabled.equalsIgnoreCase(String.valueOf(status))) {
                return;
            }
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

    public static void archiveNotification(String userid, String appId, String appName, String text, String timestamp) {
        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("notifications");

        //remove dot from email as firebase does not support it
        userid = userid.replace(".", "");
        firebaseNotificationReference = FirebaseDatabase.getInstance().getReference().child("notifications").child(userid);
        String key = firebaseNotificationReference.push().getKey();
        System.out.println(">>>>> Index: "+key);
        firebaseNotificationReference.child(key).child("appid").setValue(appId);
        firebaseNotificationReference.child(key).child("appname").setValue(appName);
        firebaseNotificationReference.child(key).child("timestamp").setValue(timestamp);
        firebaseNotificationReference.child(key).child("txt").setValue(text);

        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
////                String value = dataSnapshot.getValue(String.class);
////                System.out.println("Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                System.out.println("Failed to read value."+ error.toException());
//            }
//        });
    }

    public List<ListViewItemDTO> getEnableStatusOfApps() {
        Cursor cursor = mDatabaseHelper.getEnableStatusForAppsDB();
        List<ListViewItemDTO> result = new ArrayList<>();
        while(cursor.moveToNext()) {
            Log.d(TAG, "getEnableStatusOfApps: "+cursor.getString(1) + cursor.getString(0));
            result.add(mapToDTO(cursor.getString(0), Boolean.valueOf(cursor.getString(1)), Priority.valueOf(cursor.getString(2))));
        }
        return result;
    }

    public ListViewItemDTO mapToDTO(String name, Boolean status, Priority priority) {
        return new ListViewItemDTO(status, name, priority);
    }

    public void setAppPriority(String appName, Enum priority){
        mDatabaseHelper.setAppPriorityDB(appName,priority);
    }

}
