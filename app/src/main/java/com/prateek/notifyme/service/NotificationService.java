package com.prateek.notifyme.service;

import com.prateek.notifyme.beans.Notification;
import com.prateek.notifyme.beans.User;

import java.util.List;

public class NotificationService {
    //on receiving every notification, save target app, notification text, timestamp, priority, unreadCounter
    public void saveNotification(Notification notification){

    }

    //on app tap from dashboard, the unread counter should reset
    public void resetCounter(String appName){

    }

    //get all stored notifications
    public List<Notification> getNotifications(){

        return null;
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
