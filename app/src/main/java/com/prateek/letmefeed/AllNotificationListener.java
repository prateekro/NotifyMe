package com.prateek.letmefeed;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.prateek.letmefeed.commons.utils;

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
        Log.d(utils.TAG, "onNotificationPosted: "+ sbn.getPackageName());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.d(utils.TAG, "onNotificationRemoved: "+sbn.getPackageName() +" :: "+sbn.getNotification());

    }
}
