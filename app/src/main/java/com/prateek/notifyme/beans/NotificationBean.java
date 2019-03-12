package com.prateek.notifyme.beans;

import java.util.Date;

public class NotificationBean {
    private String id;
    private Date timestamp;
    private String appName;
    private String text;
    private String appId;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public NotificationBean() {
        id = "1111";
        appName = "whatsapp";
        appId = "com.whatsapp";
        text = "Constructor TEXT";
    }

    public NotificationBean(Date timestamp, String appName, String text, String appId) {
        this.timestamp = timestamp;
        this.appName = appName;
        this.text = text;
        this.appId = appId;

    }

    public String getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getAppName() {
        return appName;
    }

    public String getText() {
        return text;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setText(String text) {
        this.text = text;
    }

    //private String category;
}
