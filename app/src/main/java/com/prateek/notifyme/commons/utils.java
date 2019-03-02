package com.prateek.notifyme.commons;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.prateek.notifyme.R;
import com.prateek.notifyme.elements.ListElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class utils {
    public static String TAG = "TAGGER";

    public static ArrayList<ListElement> filterList(String AppName, ArrayList<ListElement> list) {
        //return list.stream().filter(s -> s.matches(regex)).collect(Collectors.toList());


        ArrayList<ListElement> indexer = new ArrayList<ListElement>();
        for (int i = 0; i < list.size(); i++) {
            Log.d(TAG, "filterList: " + AppName + " :: " + list.get(i).getAppName());
            if (list.get(i).getAppName().matches(AppName)){
                Log.d(TAG, "filterList: MATCHED***");
                indexer.add(list.get(i));
            }
            if (list.get(i).getAppName().contains(AppName)) {
                Log.d(TAG, "filterList: CONTAINS*****");
            }
        }
        return indexer;
    }

    public static String getApplicationName(String package_name, Context context){
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo( package_name, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }

        String appName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
        return appName;
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void callServiceStart(Intent notificationServiceIntent, Context context) {
        notificationServiceIntent.setAction("START_FOREGROUND");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(notificationServiceIntent);
        } else {
            context.startService(notificationServiceIntent);
        }
    }

    public static void Goto(Context context, Class<?> cls){
        context.startActivity(new Intent(context, cls));
        ((Activity) context).overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }


}


