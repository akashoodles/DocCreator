package com.example.akash.demoapplication.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akash.demoapplication.R;
import com.example.akash.demoapplication.ui.activity.PdfListActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by akash on 4/1/18.
 */

public class PdfListAdapter extends RecyclerView.Adapter<PdfListAdapter.MyViewHolder> {

    private Context context;
    private String[] listData;


    public PdfListAdapter(Context context) {

        this.context = context;

        // list = sectionTables.getSection();

    }

    public void updateListData(String[] listData ) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PdfListAdapter.MyViewHolder holder, int position) {
        holder.tvPdfName.setText(listData[position]);
    }

    @Override
    public PdfListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_pdf_list, parent, false);
        return new PdfListAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        if (listData != null)
            return listData.length;
        else
            return 0;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvPdfName;


        public MyViewHolder(View view) {
            super(view);
            tvPdfName = view.findViewById(R.id.tv_pdf_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            ((PdfListActivity)context).callBootomSheetFragment(getAdapterPosition());
        }
    }
}
