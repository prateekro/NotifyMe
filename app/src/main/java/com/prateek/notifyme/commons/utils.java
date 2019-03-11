package com.prateek.notifyme.commons;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import com.prateek.notifyme.R;
import com.prateek.notifyme.elements.ListElement;

import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

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
//        sendBroadcast(new Intent(this, MyReceiver.class).setAction("MyAction"));
//        TODO - Broadcast Receiver

//        .TODO - Broadcast Receiver



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


    public static Date convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return date;
    }

    public static String timeToString(Date time){ ;
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(time);
    }

    /**
     * Convert String of Format
     * 2019 03 10 10:22:28
     * yyyy MM dd HH:mm:ss
     * to Date
     **/
    public static Date parseToDateFromString(String dateString){
        Date parsed;
        try {
            SimpleDateFormat format =
                    //2019 03 10 10:22:28
                    //yyyy MM dd HH:mm:ss
                    new SimpleDateFormat("yyyy MM dd HH:mm:ss");
            parsed = format.parse(dateString);
            return parsed;
        }
        catch(ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    public static String getTimeAgo(Date sentTime)
    {
        Date dt = new Date();
        long milliseconds1 = sentTime.getTime();
        long milliseconds2 = dt.getTime();

        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        if (abs(diffSeconds) < 60){
            return abs(diffSeconds) + " sec ago";
        }
        long diffMinutes = diff / (60 * 1000);
        if (abs(diffMinutes) < 60){
            return abs(diffMinutes) + " min ago";
        }
        long diffHours = diff / (60 * 60 * 1000);
        if (abs(diffHours) < 24){
            return abs(diffHours) + " hrs ago";
        }
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return abs(diffDays) + " days ago";
    }

    public static String getTimeToDate(Date time){ ;
        Format format = new SimpleDateFormat("yyyy MM dd");
        return format.format(time);
    }

    public static Drawable getAppIcon(Context context, String pkg) {
        try {
            return context.getPackageManager().getApplicationIcon(pkg);
        } catch (PackageManager.NameNotFoundException e) {
            return new ColorDrawable(Color.DKGRAY);
        }
    }
    public static int getAppColor(Context context, String appPackageName) {
        try {
            final PackageManager pm = context.getPackageManager();
            // Retrieve the Resources from the app
            final Resources res = pm.getResourcesForApplication(appPackageName);
            // Create the attribute set used to get the colorPrimary color
            final int[] attrs = new int[] {
                    /** AppCompat attr */
                    res.getIdentifier("colorPrimary", "attr", appPackageName),
                    /** Framework attr */
                    android.R.attr.colorPrimary
            };

            // Create a new Theme and apply the style from the launcher Activity
            final Resources.Theme theme = res.newTheme();
            if (pm.getLaunchIntentForPackage(appPackageName) == null){
                return context.getResources().getColor(R.color.darkBlue);
            }
            final ComponentName cn = pm.getLaunchIntentForPackage(appPackageName).getComponent();
            theme.applyStyle(pm.getActivityInfo(cn, 0).theme, false);

            // Obtain the colorPrimary color from the attrs
            TypedArray a = theme.obtainStyledAttributes(attrs);
            // Do something with the color
            final int colorPrimary = a.getColor(0, a.getColor(1, Color.WHITE));
            // Make sure you recycle the TypedArray
            a.recycle();
            a = null;
            return colorPrimary;
        } catch (final PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return context.getResources().getColor(R.color.darkBlue);
        }
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}


