package com.prateek.notifyme;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.prateek.notifyme.adapter.AppListElementAdapter;
import com.prateek.notifyme.commons.utils;
import com.prateek.notifyme.elements.ListElement;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList <ListElement> appList;
    AppListElementAdapter applistadapter;
    ListView lv_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appList = new ArrayList<ListElement>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(utils.TAG, "onStart: Started");

        Button permitButton = (Button) findViewById(R.id.click_permit);
        Button notifyButton = (Button) findViewById(R.id.btn_notify);


        lv_app = (ListView) findViewById(R.id.rv_app_list_grouped);
        ListElement listElement = null;

        for (int i = 0; i < 10; i++){
            listElement  = new ListElement("Time: "+i, "Today: "+i, "Test: "+i, i + "");
            Log.d(utils.TAG, "onStart: "+ i);
//            listElement.setAppName("Test: "+i);
//            listElement.setCounter(i+" ");
//            listElement.setDate("Today: "+i);
//            listElement.setTime("Time: "+i);

            appList.add(listElement);
        }

        applistadapter = new AppListElementAdapter(this, R.layout.list_element, appList);
        lv_app.setAdapter(applistadapter);







        permitButton.setOnClickListener(tapFetcher);
        Intent intent = new Intent(this, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("Notification Title")
                                .setContentText("Notification here is read")
                                .setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, mBuilder.build());

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

                    // do something when the corky is clicked
                    startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));

                    break;
                case R.id.btn_notify:
                    // do something when the corky3 is clicked


                    break;
                default:
                    break;
            }
        }
    };
}
