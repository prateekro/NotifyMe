package com.prateek.notifyme;

public class NotificationDetail {

    String appName;
    String unreadNotification;

    public NotificationDetail(String appName, String unreadNotification) {
        this.appName = appName;
        this.unreadNotification = unreadNotification;
    }

    public String getAppName() {
        return appName;
    }

    public String getUnreadNotification() {
        return unreadNotification;
    }
}
