package com.prateek.notifyme.elements;

public class ArchiveListElement {
    private String time;
    private String date;
    private String appName;
    private String appTextExtra;
    private String sortTimeStamp;
    private String appID;
    private Integer notificationID;

    public ArchiveListElement(String time, String date, String appName, String appTextExtra, String sortTimeStamp, String appID, Integer notificationID){
        this.time = time;
        this.date = date;
        this.appName = appName;
        this.appTextExtra = appTextExtra;
        this.sortTimeStamp = sortTimeStamp;
        this.appID = appID;
        this.notificationID = notificationID;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppTextExtra() {
        return appTextExtra;
    }

    public String getSortTimeStamp() {
        return sortTimeStamp;
    }

    public String getAppID() {
        return appID;
    }

    public Integer getNotificationID() {
        return notificationID;
    }

    public void setSortTimeStamp(String sortTimeStamp) {
        this.sortTimeStamp = sortTimeStamp;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppTextExtra(String appTextExtra) {
        this.appTextExtra = appTextExtra;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setNotificationID(Integer notificationID) {
        this.notificationID = notificationID;
    }
}
