package com.prateek.notifyme.elements;

import java.util.Comparator;

public class SingleListElement {
    private String time;
    private String date;
    private String appName;
    private String counter;
    private String sortTimeStamp;
    private String appID;
    private Integer notificationID;

    public SingleListElement(String time, String date, String appName, String counter, String sortTimeStamp, String appID, Integer notificationID){
        this.time = time;
        this.date = date;
        this.appName = appName;
        this.counter = counter;
        this.sortTimeStamp = sortTimeStamp;
        this.appID = appID;
        this.notificationID = notificationID;
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

    public String getTime() { return time; }

    public String getSortTimeStamp() { return sortTimeStamp; }

    public String getAppID() {
        return appID;
    }

    public Integer getNotificationID() {
        return notificationID;
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

    public void setSortTimeStamp(String sortTimeStamp) { this.sortTimeStamp = sortTimeStamp; }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setNotificationID(Integer notificationID) {
        this.notificationID = notificationID;
    }

    /*Comparator for sorting the list by roll no*/
    public static Comparator<SingleListElement> lsTime = new Comparator<SingleListElement>() {

        public int compare(SingleListElement ls1, SingleListElement ls2) {

            String date1 = ls1.getSortTimeStamp();
            String date2 = ls2.getSortTimeStamp();

            /*For ascending order*/
//            return date1.compareTo(date2);

            /*For descending order*/
            return date2.compareTo(date1);

            //date2.compareTo(date1);
        }
    };
    /*Comparator for remove duplicate the list by text*/
    public static Comparator<SingleListElement> lsText = new Comparator<SingleListElement>() {

        public int compare(SingleListElement ls1, SingleListElement ls2) {

            String appText1 = ls1.getAppName();
            String appText2 = ls2.getAppName();

            /*For ascending order*/
//            return date1.compareTo(date2);

            /*For descending order*/
            return (appText2.equalsIgnoreCase(appText1)) ? 0 : 1;

            //date2.compareTo(date1);
        }
    };

}
