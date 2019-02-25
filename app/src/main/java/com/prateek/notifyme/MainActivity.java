package com.prateek.notifyme;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.prateek.notifyme.adapter.AppListElementAdapter;
import com.prateek.notifyme.commons.utils;
import com.prateek.notifyme.elements.ListElement;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList <ListElement> appList;
    private ListElement listElements;
    private AppListElementAdapter applistadapter;
    private ListView lv_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appList = new ArrayList<ListElement>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        Button permitButton = (Button) findViewById(R.id.click_permit);
        Button notifyButton = (Button) findViewById(R.id.btn_notify);

        lv_app = (ListView) findViewById(R.id.rv_app_list_grouped);

        for (int i = 0; i < 10; i++){
            listElements  = new ListElement("Time: "+i, "Today: "+i, "Test: "+i, i + "");
            Log.d(utils.TAG, "onStart: "+ i);

            appList.add(listElements);
        }

        applistadapter = new AppListElementAdapter(this, R.layout.list_element, appList);
        lv_app.setAdapter(applistadapter);

        lv_app.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ListElement listElement = appList.get(position);
                Toast.makeText(MainActivity.this, listElement.getAppName() +" :: date "+ listElement.getDate(), Toast.LENGTH_SHORT).show();
                Intent openNotificationListing = new Intent(getApplicationContext(), NotificationListing.class);
                openNotificationListing.putExtra("TITLE", listElement.getAppName());
                startActivity(openNotificationListing);
            }
        });







        permitButton.setOnClickListener(tapFetcher);
        notifyButton.setOnClickListener(tapFetcher);


    }

    private View.OnClickListener tapFetcher = new View.OnClickListener() {
        public void onClick(View v) {
            // do something when the button is clicked
            // Yes we will handle click here but which button clicked??? We don't know

            // So we will make
            switch (v.getId() /*to get clicked view id**/) {
                case R.id.click_permit:

                    // Call for permission
                    startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));

                    break;
                case R.id.btn_notify:
                    // do notify

                    //showNotification();
                    break;
                default:
                    break;
            }
        }
    };

}
