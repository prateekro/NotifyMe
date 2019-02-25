package com.prateek.notifyme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class NotificationListing extends AppCompatActivity {

    String pageTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_listing);

        Intent intent = getIntent();
        pageTitle = intent.getStringExtra("TITLE");

        Toast.makeText(this, "Reached on new page with title: "+pageTitle, Toast.LENGTH_SHORT).show();

    }


}
