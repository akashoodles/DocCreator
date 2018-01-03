package com.example.akash.demoapplication.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.akash.demoapplication.R;
import com.example.akash.demoapplication.constant.AppConstant;


public class IntroductionActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Button btnGenerate;
    private int position;
    private Button btnListPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
//        checkPermission();
        intiComponent();
    }

    private void intiComponent() {
        Spinner spinner = (Spinner) findViewById(R.id.spnCategory);
        btnGenerate = (Button) findViewById(R.id.btnCreate);
        btnListPdf = (Button) findViewById(R.id.btn_list_doccument);
        btnGenerate.setOnClickListener(this);
        btnListPdf.setOnClickListener(this);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.category));

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String item = adapterView.getItemAtPosition(position).toString();
        if (position == 0) {
           // Toast.makeText(this, getResources().getString(R.string.please_select_category), Toast.LENGTH_LONG).show();
        } else {
            this.position = position;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCreate:
                startActivity(new Intent(this, CameraActivity.class).putExtra(AppConstant.SOURCE, position));
                break;
            case R.id.btn_list_doccument:
                startActivity(new Intent(this, PdfListActivity.class));
                finish();
                break;
        }

    }
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    public boolean checkPermission() {
//        int currentAPIVersion = Build.VERSION.SDK_INT;
//        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                    && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//                    alertBuilder.setCancelable(true);
//                    alertBuilder.setTitle("Permission necessary");
//                    alertBuilder.setMessage("Write external storage permission is necessary to write event!!!");
//                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(IntroductionActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstant.MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
//                        }
//                    });
//                    AlertDialog alert = alertBuilder.create();
//                    alert.show();
//                } else {
//                    ActivityCompat.requestPermissions(IntroductionActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstant.MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
//                }
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return true;
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case AppConstant.MY_PERMISSIONS_REQUEST_WRITE_STORAGE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    Log.e("hello", "....");
//                } else {
//                    finish();
//                    //code for deny
//                }
//                break;
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

