package com.prateek.notifyme.elements;

import java.util.Comparator;

public class ListElement {
    private String time;
    private String date;
    private String appName;
    private String counter;
    private String appID;
//    private String getIn;

    public ListElement(String time, String date, String appName, String counter, String appID){
        this.time = time;
        this.date = date;
        this.appName = appName;
        this.counter = counter;
        this.appID = appID;
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

    public String getAppID() {
        return appID;
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

    public void setAppID(String appID) {
        this.appID = appID;
    }

    /*Comparator for sorting the list by counter*/
    public static Comparator<ListElement> lsCounter = new Comparator<ListElement>() {

        public int compare(ListElement ls1, ListElement ls2) {

            int counter1 = Integer.parseInt(ls1.getCounter());
            int counter2 = Integer.parseInt(ls2.getCounter());

            /*For ascending order*/
//            return date1.compareTo(date2);

            /*For descending order*/
            return counter2-counter1;

            //date2.compareTo(date1);
        }};

    /*Comparator for sorting the list by counter*/
    public static Comparator<ListElement> lsAppName = new Comparator<ListElement>() {

        public int compare(ListElement ls1, ListElement ls2) {

            String lsAppName1 = ls1.getAppName();
            String lsAppName2 = ls2.getAppName();

            /*For ascending order*/
//            return date1.compareTo(date2);

            /*For descending order*/
            return lsAppName2.compareTo(lsAppName1);

            //date2.compareTo(date1);
        }
    };
}


