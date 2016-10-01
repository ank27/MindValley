package com.mindvalley_ankur_khandelwal_android_test.Utils;

import android.util.Log;

import com.mindvalley_ankur_khandelwal_android_test.PinterestApplication;

public class Logger {
    public static void d(String TAG,String message){
        if(PinterestApplication.debug_mode.equals("test")) {
            Log.d(TAG, message);
        }
    }

    public static void e(String TAG,String message){
        if(PinterestApplication.debug_mode.equals("test")) {
            Log.e(TAG, message);
        }
    }

    public static void i(String TAG,String message){
        if(PinterestApplication.debug_mode.equals("test")) {
            Log.i(TAG, message);
        }
    }
}
