package com.prateek.notifyme.elements;

public class ListElement {
    private String time;
    private String date;
    private String appName;
    private String counter;
//    private String getIn;

    public ListElement(String time, String date, String appName, String counter){
        this.time = time;
        this.date = date;
        this.appName = appName;
        this.counter = counter;
    }

    public String getAppName() {
        return appName;
    }

    public String getCounter() {
        return counter;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
