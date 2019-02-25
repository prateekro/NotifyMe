package com.prateek.notifyme.commons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

//How to Use
//
// public static final String ABC = "ABC"; //-- Mention this line here in Shared preference
// MySharedPreference.putStringValue("ABC", writeValueHere);  //-- Set values to it like this - Anywhere in project
// String anyValue = MySharedPreference.getStringValue(MySharedPreference.ABC);  //-- Get values like this
public class MySharedPreference {

    public static final String PREF_NAME = "NOTIFYME_PREF";

    public static SharedPreferences mSharedPreferences;
    public static SharedPreferences.Editor mEditor;

    public static void initSharedPref(Activity mActivity) {
        mSharedPreferences = mActivity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static void putStringValue(String mKey, String mValue) {
        mEditor.putString(mKey, mValue);
        mEditor.commit();

    }
    public static void putIntValue(String mKey, int mValue) {
        mEditor.putInt(mKey, mValue);
        mEditor.commit();

    }

    public static String getStringValue(String mKey) {
        return mSharedPreferences.getString(mKey, "");
    }

    public static int getIntValue(String mKey) {
        return mSharedPreferences.getInt(mKey, 0);
    }


    public static void putBoolValue(String mKey, Boolean mValue) {
        mEditor.putBoolean(mKey, mValue);
        mEditor.commit();
    }
    public static boolean getBoolValue(String mKey) {
        return mSharedPreferences.getBoolean(mKey, true);
    }

}

