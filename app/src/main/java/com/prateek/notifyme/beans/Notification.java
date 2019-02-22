package com.prateek.notifyme.beans;

import java.util.Date;

public class Notification {
    private String id;
    private Date timestamp;
    private String appName;
    private String text;
    private String priority;

    public Notification(String id, Date timestamp, String appName, String text, String priority) {
        this.id = id;
        this.timestamp = timestamp;
        this.appName = appName;
        this.text = text;
        this.priority = priority;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
    //private String category;
}
