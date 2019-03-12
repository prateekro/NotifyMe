package com.prateek.notifyme;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prateek.notifyme.adapter.AppListElementAdapter;
import com.prateek.notifyme.beans.NotificationBean;
import com.prateek.notifyme.commons.MySharedPreference;
import com.prateek.notifyme.commons.utils;
import com.prateek.notifyme.elements.ListElement;
import com.prateek.notifyme.service.NotificationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private Intent notificationServiceIntent;
    public ArrayList <ListElement> appList;
    private ListElement listElements;
    AppListElementAdapter applistadapter;
    private ListView lv_app;
    public static MySharedPreference mySharedPreference;
    //    public static SQLiteHelper mDatabaseHelper;
    FloatingActionButton fabMenu;
    Timer timerHandler;
    TimerTask timedNotificationUpdate;

    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        notificationServiceIntent = new Intent(getApplicationContext(), AllNotificationListener.class);
//        mDatabaseHelper = new SQLiteHelper(getApplicationContext(),null,null, 1);

        //Start Service
        if (!utils.isMyServiceRunning(AllNotificationListener.class, getApplicationContext())) {
            Log.d(utils.TAG, "##### onCreate: Service initiated");
            // Call for permission
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            Toast.makeText(this, "Allow NotifyMe to run in background by enabling toggle in Notification Access", Toast.LENGTH_LONG).show();

            utils.callServiceStart(notificationServiceIntent, getApplicationContext());
        }else {
            Log.d(utils.TAG, "##### onCreate: Service Running already");
        }
        //.Start Service

        appList = new ArrayList<ListElement>();

        lv_app = (ListView) findViewById(R.id.rv_app_list_grouped);

        mySharedPreference.initSharedPref(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();

//        timerHandler = new Timer();

//        timerHandler.schedule(timedNotificationUpdate, 0, 3000);

        //firebase - get reference
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("notifications").child("user1");

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

    @Override
    protected void onStart() {
        super.onStart();

//        Button permitButton = (Button) findViewById(R.id.click_permit);
//        Button notifyButton = (Button) findViewById(R.id.btn_notify);

        fabMenu = (FloatingActionButton) findViewById(R.id.fabMenu);

        applistadapter = new AppListElementAdapter(this, R.layout.list_element, appList);
        lv_app.setAdapter(applistadapter);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.rv_layout_animation);
        lv_app.setLayoutAnimation(controller);
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

        fabMenu.setOnClickListener(tapFetcher);



//        permitButton.setOnClickListener(tapFetcher);
//        notifyButton.setOnClickListener(tapFetcher);

//        Button logOut = (Button) findViewById(R.id.log_out);
//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        //TODO  - DO IT ON SETTINGS LOG OUT-  mAuth.signOut();

//
//            }
//        }]]['


        //test archive storage
        NotificationService.archiveNotification("sam@gy.com", "com.whatsapp", "Whatsapp", "Test notify","2345678901");

        //get archived notification code
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
//                NotificationBean notification = dataSnapshot.getValue(NotificationBean.class);
//                HashMap map = dataSnapshot.getValue(HashMap.class);
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                System.out.println("### YeP! ");
                for (Object key : map.keySet()) {
                    System.out.println("//// "+map.get(key));
                    // Set data in View list
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println("^^^ ERROR: "+ databaseError.toString());
            }
        };
        mPostReference.addValueEventListener(postListener);
        mPostListener = postListener;
    }

    @Override
    protected void onPause() {
        super.onPause();
        timedNotificationUpdate.cancel();
        timerHandler.cancel();
        timerHandler.purge();
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
        timerHandler.schedule(timedNotificationUpdate, 0, 3000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Remove post value event listener
        if (mPostListener != null) {
            mPostReference.removeEventListener(mPostListener);
        }
    }

    private View.OnClickListener tapFetcher = new View.OnClickListener() {
        public void onClick(View v) {
            // do something when the button is clicked
            // Yes we will handle click here but which button clicked??? We don't know

            // So we will make
            switch (v.getId() /*to get clicked view id**/) {
//                case R.id.click_permit:
//
//                    // Call for permission
//                    startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
//                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//
//                    break;
//                case R.id.btn_notify:
//                    // do notify
//                    //ToDO - Refactor to somewhere - code
////                    updateListOfNotifications();
//
//                    //showNotification();
//                    break;
                case R.id.fabMenu:
                    startActivity(new Intent(MainActivity.this, SettingPageActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                    break;
                default:
                    break;
            }
        }
    };

    public void updateListOfNotifications(){

        Log.d(utils.TAG, "%%%%%%%: UPDATE CALLED");
        NotificationService notificationService = new NotificationService(getApplicationContext());
        HashMap<String, Integer> myAllNotifications = notificationService.getAllNotifications();
        Set appNames = myAllNotifications.keySet();
        Log.d(utils.TAG, "%%%%%%%: KEYS "+ appNames);
        int i=0;
        for(Object appName: appNames) {
            if (i == 0){
                appList.clear();
            }
            Log.d(utils.TAG, "onClick: App: "+ appName.toString()+" Unread: "+myAllNotifications.get(appName));
            listElements  = new ListElement(" ", " ", appName.toString(), myAllNotifications.get(appName).toString());
            appList.add(listElements);
            i++;
        }

        applistadapter.notifyDataSetInvalidated();
        applistadapter.notifyDataSetChanged();

        //ToDO - Refactor to somewhere - with Trigger by (implement) broadcast receiver on Any notification received - 24/02/2019 - Code by Prateek Rokadiya
//        if (appNamesUniqueList != null){
//            int i=0;
//            for (String appName :appNamesUniqueList){
//                if (i == 0){
//                    appList.clear();
//                }
//                listElements  = new ListElement("Time: "+i, "Today: "+i, appName + " : "+i, 0 + "");
//                appList.add(listElements);
//                i++;
//            }
//        }
//        applistadapter.notifyDataSetInvalidated();
//        applistadapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
    }
}
