package com.mindvalley_ankur_khandelwal_android_test;

import android.app.Application;

import com.mindvalley_ankur_khandelwal_android_test.Model.PinModel;
import com.mindvalley_ankur_khandelwal_android_test.Utils.VolleySingleton;

import java.util.ArrayList;

public class PinterestApplication extends Application {
    public static String debug_mode="test";
    public static PinModel selectedPinModels;
    @Override
    public void onCreate(){
        super.onCreate();
        VolleySingleton.init(this);
    }
}
