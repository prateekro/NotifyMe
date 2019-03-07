package com.prateek.notifyme;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.prateek.notifyme.beans.ApplicationBean;
import com.prateek.notifyme.beans.NotificationBean;
import com.prateek.notifyme.commons.utils;
import com.prateek.notifyme.elements.ListElement;
import com.prateek.notifyme.service.NotificationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.prateek.notifyme.MainActivity.mySharedPreference;
import static com.prateek.notifyme.commons.utils.TAG;

public class AllNotificationListener extends NotificationListenerService {

    public static Set<String> appNamesUniqueList;
    public static ArrayList<ListElement> ListofAllNotification;
    private ListElement listElement;

    Notification notification;
    private String notification_title = "NotifyMe";
    private String notification_message = "NotifyMe - Background Service";

    private String appName;

    public AllNotificationListener() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();



        ListofAllNotification = new ArrayList<ListElement>();
        appNamesUniqueList = new HashSet<String>();

        String CHANNEL_DEFAULT_IMPORTANCE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?  "1" : "";

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE);
        notification = notificationBuilder
                .setContentTitle(notification_title)
                .setContentText(notification_message)
                .setSmallIcon(R.mipmap.icon_round)
                .setTicker(notification_title)
                .build();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        appName = utils.getApplicationName(sbn.getPackageName(), getApplicationContext());
        NotificationBean notificationBean = new NotificationBean(utils.convertTime(sbn.getPostTime()), appName.toString(), sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString(), sbn.getPackageName().toString());
        ApplicationBean applicationBean = new ApplicationBean(sbn.getPackageName().toString(), appName, true );
        NotificationService notificationService = new NotificationService(getApplicationContext());
        notificationService.saveNotification(notificationBean,applicationBean);

        Log.d(TAG, "onNotificationPosted: AppName: "+ appName);
        appNamesUniqueList.add(appName);

        listElement = new ListElement(String.valueOf(sbn.getPostTime()), String.valueOf(sbn.getPostTime()), appName, sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString());
        ListofAllNotification.add(listElement);

        Log.d(TAG, "onNotificationPosted TITILE: "+ sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TITLE).toString());
        Log.d(TAG, "onNotificationPosted TEXT: "+ sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString());
        Log.d(TAG, "onNotificationPosted DescibeContents: "+ sbn.describeContents());

//        Log.d(TAG, "onNotificationPosted: "+ sbn.getKey());
//        if (sbn.getKey().equals("0|com.whatsapp|1|null|10117")){
//            // To cancel notification
//            // cancelNotification("0|com.whatsapp|1|null|10117"); key
//
//        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.d(TAG, "onNotificationRemoved: "+sbn.getPackageName() +" :: "+sbn.getNotification());
        appName = utils.getApplicationName(sbn.getPackageName(), getApplicationContext());
        Log.d(TAG, "onNotificationRemoved: AppName: "+ appName);
        appNamesUniqueList.add(appName);

    }

    @Override
    public void onInterruptionFilterChanged(int interruptionFilter) {
        super.onInterruptionFilterChanged(interruptionFilter);

        //Detect mode change : Like dnd to silent, or silent to dnd, or normal to dnd
        Log.d(TAG, "onInterruptionFilterChanged: ------------------------");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public StatusBarNotification[] getActiveNotifications() {
        for (StatusBarNotification notification: getActiveNotifications()){
            appName = utils.getApplicationName(notification.getPackageName(), getApplicationContext());
            Log.d(TAG, "getCurrentNotificationsInStatusBar: AppName: "+ appName);
            appNamesUniqueList.add(appName);
        }
        return super.getActiveNotifications();
    }

    class NotificationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
