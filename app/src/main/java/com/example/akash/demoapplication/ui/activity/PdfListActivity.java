package com.example.akash.demoapplication.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akash.demoapplication.R;
import com.example.akash.demoapplication.constant.AppConstant;
import com.example.akash.demoapplication.ui.fragment.PdfOperationFragment;
import com.example.akash.demoapplication.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PdfListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_list);
        initComponent();
    }

    private void initComponent() {
        listView=(ListView)findViewById(R.id.lv_pdf_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, FileUtils.getOutputMediaFileForPdf().list());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            PdfOperationFragment bottomSheetDialogFragment = new PdfOperationFragment();
               Bundle bundle = new Bundle();
              bundle.putInt(AppConstant.PDF_POSITION, i);
                bottomSheetDialogFragment.setArguments(bundle);
              bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
       // showCustomDialog(i);
        Toast.makeText(this,"position "+i,Toast.LENGTH_LONG);
    }



    @Override
    public void onBackPressed() {
startActivity(new Intent(this,IntroductionActivity.class));
        finish();
    }




}