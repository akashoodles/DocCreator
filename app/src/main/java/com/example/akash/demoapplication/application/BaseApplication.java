package com.example.akash.demoapplication.application;

import android.app.Application;
import android.os.StrictMode;

import com.example.akash.demoapplication.utils.FileUtils;

/**
 * Created by akash on 15/12/17.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
}
