package com.prateek.notifyme;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
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
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.prateek.notifyme.R;
import android.widget.Toast;
import com.prateek.notifyme.adapter.AppListElementAdapter;
import com.prateek.notifyme.commons.MySharedPreference;
import com.google.firebase.auth.FirebaseAuth;
import com.prateek.notifyme.R;
import com.prateek.notifyme.adapter.AppListElementAdapter;
import com.prateek.notifyme.commons.MySharedPreference;
import com.prateek.notifyme.commons.utils;
import com.prateek.notifyme.elements.ListElement;
import com.prateek.notifyme.service.NotificationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static com.prateek.notifyme.AllNotificationListener.appNamesUniqueList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private Intent notificationServiceIntent;
    private ArrayList <ListElement> appList;
    private ListElement listElements;
    private AppListElementAdapter applistadapter;
    private ListView lv_app;
    public static MySharedPreference mySharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        notificationServiceIntent = new Intent(getApplicationContext(), AllNotificationListener.class);

        //Start Service
        if (!utils.isMyServiceRunning(AllNotificationListener.class, getApplicationContext())) {
            Log.d(utils.TAG, "onCreate: Service Started");
            utils.callServiceStart(notificationServiceIntent, getApplicationContext());
        }else {
            Log.d(utils.TAG, "onCreate: Service Running already");
        }
        //.Start Service

        appList = new ArrayList<ListElement>();

        lv_app = (ListView) findViewById(R.id.rv_app_list_grouped);

        mySharedPreference.initSharedPref(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        Button permitButton = (Button) findViewById(R.id.click_permit);
        Button notifyButton = (Button) findViewById(R.id.btn_notify);

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
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });



        permitButton.setOnClickListener(tapFetcher);
        notifyButton.setOnClickListener(tapFetcher);

        Button logOut = (Button) findViewById(R.id.log_out);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });
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
                    NotificationService notificationService = new NotificationService();
                    HashMap<String, Integer> myAllNotifications= notificationService.getAllNotifications();
                    Set keys = myAllNotifications.keySet();
                    for(Object k: keys) {
                        System.out.println("App: "+k.toString()+" Unread: "+myAllNotifications.get(k));
                    }

                    //ToDO - Refactor to somewhere - with Trigger by (implement) broadcast receiver on Any notification received - 24/02/2019 - Code by Prateek Rokadiya
                    if (appNamesUniqueList != null){
                        int i=0;
                        for (String appName :appNamesUniqueList){
                            if (i == 0){
                                appList.clear();
                            }
                            listElements  = new ListElement("Time: "+i, "Today: "+i, appName + " : "+i, 0 + "");
                            appList.add(listElements);
                            i++;
                        }
                    }
                    applistadapter.notifyDataSetInvalidated();
                    applistadapter.notifyDataSetChanged();
                    //ToDO - Refactor to somewhere - code

                    //showNotification();
                    break;
                default:
                    break;
            }
        }
    };
}
