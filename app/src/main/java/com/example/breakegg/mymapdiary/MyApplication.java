package com.example.breakegg.mymapdiary;

import android.app.Application;
import android.content.Context;

/**
 * Created by break on 2018/2/10.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext() {
        return context;
    }
}
