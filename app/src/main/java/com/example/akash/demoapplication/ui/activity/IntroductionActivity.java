package com.example.akash.demoapplication.ui.activity;

import android.animation.Animator;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.akash.demoapplication.R;
import com.example.akash.demoapplication.constant.AppConstant;


public class IntroductionActivity extends BaseActivity implements  View.OnClickListener {

    private FloatingActionButton btnGenerate;
    private int position;
    private Button btnListPdf;
    private RadioButton pdfBtn;
    private LinearLayout pdfLayout,docLayout;
    private FloatingActionButton pdfFab,docFab,baseFab;
    private View fabBGLayout;
    boolean isFABOpen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
//        checkPermission();
        intiComponent();
    }

    private void intiComponent() {
        //Spinner spinner = (Spinner) findViewById(R.id.spnCategory);
        pdfBtn=(RadioButton)findViewById(R.id.rbtn_pdf);
        //docBtn=(RadioButton)findViewById(R.id.rbtn_doc);
        pdfBtn.setChecked(true);



        pdfLayout= (LinearLayout) findViewById(R.id.fabLayout1);
        docLayout= (LinearLayout) findViewById(R.id.fabLayout2);
        pdfFab = (FloatingActionButton) findViewById(R.id.fab1);
        docFab= (FloatingActionButton) findViewById(R.id.fab2);
        baseFab = (FloatingActionButton) findViewById(R.id.fab3);
        fabBGLayout=findViewById(R.id.fabBGLayout);

        baseFab.setOnClickListener(this);
        docFab.setOnClickListener(this);
        pdfFab.setOnClickListener(this);

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
            }
        });
    }
        /*
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.category));

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);*/




    private void showFABMenu(){
        isFABOpen=true;
        pdfLayout.setVisibility(View.VISIBLE);
        docLayout.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        baseFab.animate().rotationBy(135);
        pdfLayout.animate().translationY(-getResources().getDimension(R.dimen.fifty_five_dp));
        docLayout.animate().translationY(-getResources().getDimension(R.dimen.hundred_dp));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabBGLayout.setVisibility(View.GONE);
        baseFab.animate().rotationBy(-135);
        pdfLayout.animate().translationY(0);
        docLayout.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    pdfLayout.setVisibility(View.GONE);
                    docLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    /*@Override
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

    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab1:
                position=0;
                Log.e("status","**** position : "+position);
                startActivity(new Intent(this, CameraActivity.class).putExtra(AppConstant.SOURCE, position));
                break;

            case R.id.fab2:
                position=1;
                Log.e("status","**** position : "+position);
                startActivity(new Intent(this, CameraActivity.class).putExtra(AppConstant.SOURCE, position));
                break;

            case R.id.fab3:
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
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

