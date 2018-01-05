package com.example.akash.demoapplication.ui.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.akash.demoapplication.R;
import com.example.akash.demoapplication.constant.AppConstant;
import com.example.akash.demoapplication.ui.adapter.PdfListAdapter;
import com.example.akash.demoapplication.ui.fragment.PdfOperationFragment;
import com.example.akash.demoapplication.utils.FileUtils;

public class PdfListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private PdfListAdapter adapter;
    private int position;
    private LinearLayout pdfLayout, docLayout, listLayout, emptyLayout;
    private FloatingActionButton pdfFab, docFab, baseFab;
    private View fabBGLayout;
    boolean isFABOpen = false;
    private String[] listPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initComponent();
    }

    private void initComponent() {
        emptyLayout = (LinearLayout) findViewById(R.id.empty_layout);
        listLayout = (LinearLayout) findViewById(R.id.list_layout);
        pdfLayout = (LinearLayout) findViewById(R.id.fabLayout1);
        docLayout = (LinearLayout) findViewById(R.id.fabLayout2);
        pdfFab = (FloatingActionButton) findViewById(R.id.fab1);
        docFab = (FloatingActionButton) findViewById(R.id.fab2);
        baseFab = (FloatingActionButton) findViewById(R.id.fab3);
        fabBGLayout = findViewById(R.id.fabBGLayout);

        baseFab.setOnClickListener(this);
        docFab.setOnClickListener(this);
        pdfFab.setOnClickListener(this);

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
            }
        });
        listPdf = FileUtils.getOutputMediaFileForPdf().list();
        recyclerView = (RecyclerView) findViewById(R.id.rcy_pdf_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (listPdf.length > 0) {
            showPdfList(listPdf);

        } else {
            listLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);

        }

    }

    private void showPdfList(String[] listPdf) {
        listLayout.setVisibility(View.VISIBLE);
        emptyLayout.setVisibility(View.GONE);
        if (adapter == null) {
            adapter = new PdfListAdapter(this);
            recyclerView.setAdapter(adapter);
        }


        adapter.updateListData(listPdf);
    }

    private void showFABMenu() {
        isFABOpen = true;
        pdfLayout.setVisibility(View.VISIBLE);
        docLayout.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        baseFab.animate().rotationBy(135);
        pdfLayout.animate().translationY(-getResources().getDimension(R.dimen.fifty_five_dp));
        docLayout.animate().translationY(-getResources().getDimension(R.dimen.hundred_dp));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        baseFab.animate().rotationBy(-135);
        pdfLayout.animate().translationY(0);
        docLayout.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
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

    public void callBootomSheetFragment(int position) {
        PdfOperationFragment bottomSheetDialogFragment = new PdfOperationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.PDF_POSITION, position);
        bottomSheetDialogFragment.setArguments(bundle);
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PdfOperationFragment bottomSheetDialogFragment = new PdfOperationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.PDF_POSITION, i);
        bottomSheetDialogFragment.setArguments(bundle);
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
        // showCustomDialog(i);
        Toast.makeText(this, "position " + i, Toast.LENGTH_LONG);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab1:
                position = 0;
                Log.e("status", "**** position : " + position);
                closeFABMenu();
                startActivityForResult(new Intent(this, CameraActivity.class).putExtra(AppConstant.SOURCE, position), AppConstant.REQ_PDFACTIVITY);
                break;

            case R.id.fab2:
                position = 1;
                Log.e("status", "**** position : " + position);
                closeFABMenu();
                startActivityForResult(new Intent(this, CameraActivity.class).putExtra(AppConstant.SOURCE, position), AppConstant.REQ_PDFACTIVITY);
                break;

            case R.id.fab3:
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.REQ_PDFACTIVITY) {

            if (resultCode == AppConstant.RES_PDFACTIVITY) {
                showPdfList(FileUtils.getOutputMediaFileForPdf().list());
            }


        }
    }

}
