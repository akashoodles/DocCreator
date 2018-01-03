package com.example.akash.demoapplication.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.akash.demoapplication.listeners.PermissionResultCallback;
import com.example.akash.demoapplication.utils.PermissionUtils;

import java.util.ArrayList;


/**
 * Created by Donny Dominic on 17-05-2017.
 */

public class BaseActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,PermissionResultCallback
         {

    public boolean onlynet;
    private DisplayMetrics displayMetrics;
    int height ;
    int width;
             ArrayList<String> permissions=new ArrayList<>();
             private PermissionUtils permissionUtils;

             @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionUtils=new PermissionUtils(this);

        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
           getDeviceDimension();
                 permissionUtils.check_permission(permissions,"Write external storage and camera permission is necessary to take and store picture!!!",1);

             }

    private void getDeviceDimension() {

        displayMetrics = new DisplayMetrics();

        WindowManager windowmanager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        height=displayMetrics.heightPixels;
        width=displayMetrics.widthPixels;

        Log.e("widht and height","  "+width+" "+height);


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


             @Override
             public void PermissionGranted(int request_code) {

             }

             @Override
             public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
                 finish();

             }

             @Override
             public void PermissionDenied(int request_code) {
                 finish();

             }

             @Override
             public void NeverAskAgain(int request_code) {
                 finish();

             }



             @Override
             public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                    @NonNull int[] grantResults) {

                 // redirects to utils

                 permissionUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);

             }
         }
