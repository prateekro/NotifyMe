package com.prateek.notifyme;

import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prateek.notifyme.adapter.AppListElementAdapter;
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

import static com.prateek.notifyme.commons.utils.getAppColor;
import static com.prateek.notifyme.commons.utils.getAppIcon;
import static com.prateek.notifyme.commons.utils.TAG;

public class NotificationListing extends AppCompatActivity {

    String pageTitle = "", pagePKG = "";
    TextView tv_appname;
    ImageView iv_appIcon;
    CardView cv_appBack;
    public static ArrayList<SingleListElement> notificationList;
    private SingleAppListAdapter mAppListElementAdapter;
    private ListView lv_listing;
    Timer timerHandler;
    TimerTask timedNotificationUpdate;
    FloatingActionButton clearButton;

    AlertDialog.Builder alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_listing);

        Intent intent = getIntent();
        pageTitle = intent.getStringExtra("TITLE");
        pagePKG = intent.getStringExtra("PKG");

        notificationList = new ArrayList<SingleListElement>();
        lv_listing = (ListView) findViewById(R.id.lv_listing);
        tv_appname = (TextView) findViewById(R.id.tv_listing);
        iv_appIcon = (ImageView) findViewById(R.id.iv_listing);
        cv_appBack = (CardView) findViewById(R.id.cv_appBack);
        clearButton = (FloatingActionButton) findViewById(R.id.clearId);

        //test code
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("notifications");
//        myRef.setValue("Hello, World!");
//        myRef.setValue("Check instance");
//        myRef.child("user1").child("txt").setValue("text 2");
        String txt = String.valueOf(myRef.child("user1").child("instagram").child("1").child("txt"));
        System.out.println("*****");
        System.out.println(txt);

        alertDialog = new AlertDialog.Builder(NotificationListing.this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        tv_appname.setText(pageTitle);
        alertDialog.setTitle(pageTitle);
        alertDialog.setIcon(getAppIcon(NotificationListing.this, pagePKG));
        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton("Read!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        Log.d(TAG, "APP COLOR$$: " + getAppColor(NotificationListing.this, pagePKG));
        int appColor = getAppColor(NotificationListing.this, pagePKG);
        if (appColor != -1) {
            tv_appname.setTextColor(appColor);
        }

        iv_appIcon.setImageDrawable(getAppIcon(NotificationListing.this, pagePKG));

        Palette.from(utils.drawableToBitmap(getAppIcon(NotificationListing.this, pagePKG))).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                // Use generated instance //https://developer.android.com/training/material/palette-colors
                tv_appname.setTextColor(p.getDominantColor(getResources().getColor(R.color.darkBlue)));
//                Palette.Swatch vibrantSwatch = p.getVibrantSwatch();
//                tv_appname.setTextColor(vibrantSwatch.getTitleTextColor());
//                cv_appBack.setCardBackgroundColor(p.getLightMutedColor(getResources().getColor(R.color.lighterBlue)));
            }
        });


        mAppListElementAdapter = new SingleAppListAdapter(this, R.layout.single_element, notificationList);
        lv_listing.setAdapter(mAppListElementAdapter);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.rv_layout_animation);
        lv_listing.setLayoutAnimation(controller);

        AdapterView.OnItemLongClickListener itemClickListener = new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d(TAG, "onItemLongClick: LONG CLICKED at: "+position);
                alertDialog.setMessage(notificationList.get(position).getAppName());
                alertDialog.show();
                return false;
            }

        };

        lv_listing.setOnItemLongClickListener(itemClickListener);

        lv_listing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d(TAG, "onItemClick: CLICKED at: " + position);
                try {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(pagePKG);
                    if (launchIntent != null)
                        startActivity(launchIntent);
                    else
                        Toast.makeText(NotificationListing.this, "Target App not found", Toast.LENGTH_SHORT).show();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final NotificationService notificationService = new NotificationService(getApplicationContext());
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationList.clear();
                notificationService.clearAllNotifications(pageTitle);
                mAppListElementAdapter.notifyDataSetInvalidated();
                mAppListElementAdapter.notifyDataSetChanged();
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

    public void updateListOfNotifications() {
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
            //txt time pkg
            Log.d(utils.TAG, "DATA OF KEY:: TEXT: "+appAllNotifications.get(appID).get(0)
                    + " ::TimeStamp: "+ appAllNotifications.get(appID).get(1)
                    + " ::APPID: "+ appAllNotifications.get(appID).get(2)
            );
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
                            dt.toString(),
                            appAllNotifications.get(appID).get(2).toString(), //Gets appID
                            Integer.parseInt(appID.toString())
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
        Log.d(TAG, "onBackPressed: pressed");
        new NotificationService(getApplicationContext()).resetCounter(pageTitle); //reset the counter
        finish();
        overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
    }
}
