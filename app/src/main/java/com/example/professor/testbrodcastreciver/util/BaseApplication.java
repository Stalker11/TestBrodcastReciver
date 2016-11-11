package com.example.professor.testbrodcastreciver.util;

import android.app.Application;
import android.content.SharedPreferences;

public class BaseApplication extends Application {
    public static SharedPreferences sPref;
    @Override
    public void onCreate() {
        super.onCreate();
        sPref = getSharedPreferences(getPackageName(),MODE_PRIVATE);
    }
}
