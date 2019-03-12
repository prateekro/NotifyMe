package com.prateek.notifyme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prateek.notifyme.adapter.AppListElementAdapter;
import com.prateek.notifyme.commons.utils;
import com.prateek.notifyme.elements.ListElement;

import java.util.ArrayList;

public class NotificationListing extends AppCompatActivity {

    String pageTitle = "";
    private ArrayList<ListElement> notificationList;
    private AppListElementAdapter mAppListElementAdapter;
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

        //test code
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("notifications");
//        myRef.setValue("Hello, World!");
//        myRef.setValue("Check instance");
//        myRef.child("user1").child("txt").setValue("text 2");
        String txt = String.valueOf(myRef.child("user1").child("instagram").child("1").child("txt"));
        System.out.println("*****");
        System.out.println(txt);


    }

    @Override
    protected void onStart() {
        super.onStart();


        notificationList = utils.filterList(pageTitle, AllNotificationListener.ListofAllNotification);
        mAppListElementAdapter = new AppListElementAdapter(this, R.layout.list_element, notificationList);
        lv_listing.setAdapter(mAppListElementAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
    }
}
