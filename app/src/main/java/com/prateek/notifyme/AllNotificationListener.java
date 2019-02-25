package com.prateek.notifyme;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.prateek.notifyme.commons.utils;

import static com.prateek.notifyme.commons.utils.TAG;

public class AllNotificationListener extends NotificationListenerService {
    public AllNotificationListener() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Log.d(TAG, "onNotificationPosted: "+ sbn.getPackageName());
        Log.d(TAG, "onNotificationPosted: "+ sbn.getKey());
        //To cancel notification
//        cancelNotification("0|com.whatsapp|1|null|10117"); key
        if (sbn.getKey().equals("0|com.whatsapp|1|null|10117")){

        }

        Log.d(TAG, "onNotificationPosted: "+ sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString());
        Log.d(TAG, "onNotificationPosted: "+ sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TITLE).toString());
        Log.d(TAG, "onNotificationPosted: "+ sbn.describeContents());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.d(TAG, "onNotificationRemoved: "+sbn.getPackageName() +" :: "+sbn.getNotification());

    }

    @Override
    public void onInterruptionFilterChanged(int interruptionFilter) {
        super.onInterruptionFilterChanged(interruptionFilter);

        //Detect mode change : Like dnd to silent, or silent to dnd, or normal to dnd
        Log.d(TAG, "onInterruptionFilterChanged: ------------------------");



    }


}
