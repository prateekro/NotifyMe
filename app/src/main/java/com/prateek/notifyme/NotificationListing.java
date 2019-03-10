package com.prateek.notifyme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.prateek.notifyme.adapter.SingleAppListAdapter;
import com.prateek.notifyme.commons.utils;
import com.prateek.notifyme.elements.ListElement;
import com.prateek.notifyme.service.NotificationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class NotificationListing extends AppCompatActivity {

    String pageTitle = "";
    private ArrayList<ListElement> notificationList;
    private SingleAppListAdapter mAppListElementAdapter;
    private ListView lv_listing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_listing);

        Intent intent = getIntent();
        pageTitle = intent.getStringExtra("TITLE");

        notificationList = new ArrayList<ListElement>();
        lv_listing = (ListView) findViewById(R.id.lv_listing);

        Toast.makeText(this, "Reached on new page with title: "+pageTitle, Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onStart() {
        super.onStart();

        notificationList = utils.filterList(pageTitle, AllNotificationListener.ListofAllNotification);

        NotificationService notificationService = new NotificationService(getApplicationContext());
        Log.d(utils.TAG, "APPNAME _ FOR KEYS: "+pageTitle);
        HashMap<String, Integer> appAllNotifications = notificationService.getAppNotifications(pageTitle);
        Set appText = appAllNotifications.keySet();
        Log.d(utils.TAG, "%%%%%%%: SINGLE KEYS "+ appText);



        mAppListElementAdapter = new SingleAppListAdapter(this, R.layout.list_element, notificationList);
        lv_listing.setAdapter(mAppListElementAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
    }
}
