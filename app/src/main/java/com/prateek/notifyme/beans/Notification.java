package com.prateek.notifyme.beans;

import java.util.Date;

public class Notification {
    private String id;
    private Date timestamp;
    private String appName;
    private String text;
    private String appId;

    public Notification(String id, String appId, Date timestamp, String appName, String text) {
        this.id = id;
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

}
