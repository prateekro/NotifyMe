package com.prateek.notifyme.service;

import com.prateek.notifyme.beans.Notification;
import com.prateek.notifyme.beans.User;

import java.util.Date;
import java.util.List;

public class NotificationService {
    //on receiving every notification, save target app, notification text, timestamp, priority, unreadCounter
    private void saveNotification(Notification notification){
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
    private void resetCounter(String appName){

    }

    //get all stored notifications
    private List<Notification> getNotifications(){

        return null;
    }

    //delete all notifications of a particular app
    private void deleteNotification(String appName){

    }

    //delete all notifications
    private void clearAllNotifications(){

    }

    //delete a particular notification
    private void deleteNotification(Notification notification){

    }
}
