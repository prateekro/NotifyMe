package com.prateek.notifyme.elements;

import java.util.Comparator;

public class SingleListElement {
    private String time;
    private String date;
    private String appName;
    private String counter;
    private String sortTimeStamp;

    public SingleListElement(String time, String date, String appName, String counter, String sortTimeStamp){
        this.time = time;
        this.date = date;
        this.appName = appName;
        this.counter = counter;
        this.sortTimeStamp = sortTimeStamp;
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
        }};

}
