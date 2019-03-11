package com.prateek.notifyme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ListView;

import com.prateek.notifyme.adapter.SingleAppListAdapter;
import com.prateek.notifyme.commons.utils;
import com.prateek.notifyme.elements.ListElement;
import com.prateek.notifyme.elements.SingleListElement;
import com.prateek.notifyme.service.NotificationService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationListing extends AppCompatActivity {

    String pageTitle = "";
    private ArrayList<SingleListElement> notificationList;
    private SingleAppListAdapter mAppListElementAdapter;
    private ListView lv_listing;
    Timer timerHandler;
    TimerTask timedNotificationUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_listing);

        Intent intent = getIntent();
        pageTitle = intent.getStringExtra("TITLE");

        notificationList = new ArrayList<SingleListElement>();
        lv_listing = (ListView) findViewById(R.id.lv_listing);

    }

    @Override
    protected void onStart() {
        super.onStart();



        mAppListElementAdapter = new SingleAppListAdapter(this, R.layout.single_element, notificationList);
        lv_listing.setAdapter(mAppListElementAdapter);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.rv_layout_animation);
        lv_listing.setLayoutAnimation(controller);
        lv_listing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


            }
        });

    }


    private void TimerMethod() {
        //This method is called directly by the timer and runs in the same thread as the timer.
        this.runOnUiThread(Timer_Tick);
        //We call the method that will work with the UI through the runOnUiThread method.
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            //This method runs in the same thread as the UI.
            updateListOfNotifications();
            //Do something to the UI thread here
        }
    };

    private void updateListOfNotifications() {
        NotificationService notificationService = new NotificationService(getApplicationContext());
        HashMap<String, ArrayList<String>> appAllNotifications = notificationService.getAppNotifications(pageTitle);
        Set keySetAppID = appAllNotifications.keySet();
        Log.d(utils.TAG, "APPNAME _ FOR KEYS: "+pageTitle);
        Log.d(utils.TAG, "%%%%%%%: SINGLE KEYS SIZE: "+ keySetAppID.size());
        int i = 0;
        for (Object appID: keySetAppID){
            if (i == 0){
                notificationList.clear();
            }
            i++;
            Log.d(utils.TAG, "DATA OF KEY:: TEXT: "+appAllNotifications.get(appID).get(0) + " ::TimeStamp: "+ appAllNotifications.get(appID).get(1));
            Date dt = utils.parseToDateFromString(appAllNotifications.get(appID).get(1));

            if (appAllNotifications.get(appID).get(0).matches(" *")
                    || appAllNotifications.get(appID).get(0).matches("[0-9]*(new| |messages|from|chats|\\d)*") //27 messages from 10 chats
//                    || appAllNotifications.get(appID).get(0).matches("[0-9]*(new| |messages)*") //2 new messages
            ) //No empty app text
            {
                continue;
            }
//            if (notificationList.contains() )

            notificationList.add(
                    new SingleListElement(
                            utils.getTimeAgo(dt), //Get back days ago /or hrs ago /or min ago /or sec ago
                            utils.getTimeToDate(dt), //Gets only the date
                            appAllNotifications.get(appID).get(0), //Puts the extra text from notification
                            " ",
                            dt.toString() //Gets only the timestamp
                    ) //Counter isn't required here.
            );

        }
//        notificationList = new ArrayList<SingleListElement>(new LinkedHashSet<SingleListElement>(notificationList));
        Collections.sort(notificationList, SingleListElement.lsTime);

        mAppListElementAdapter.notifyDataSetInvalidated();
        mAppListElementAdapter.notifyDataSetChanged();

        //            //LOGS
        //            Log.d(utils.TAG, "RTRNS AGO (A)(): "+dt);
        //            Log.d(utils.TAG, "RTRNS AGO (B)(): "+utils.getTimeAgo(dt));
        //            Log.d(utils.TAG, "RTRNS AGO (C)(): "+utils.getTimeToDate(dt));
        //            //.Logs

    }

    @Override
    protected void onResume() {
        super.onResume();
        timerHandler = new Timer();
        timedNotificationUpdate = new TimerTask() {
            public void run() {
                TimerMethod();
                // here goes my code
            }
        };
        timerHandler.schedule(timedNotificationUpdate, 0, 15000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timedNotificationUpdate.cancel();
        timerHandler.cancel();
        timerHandler.purge();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
    }
}
