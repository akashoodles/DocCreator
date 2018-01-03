package com.example.akash.demoapplication.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.akash.demoapplication.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by akash on 13/12/17.
 */
public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> listData;


    public PicturesAdapter(Context contex) {

        this.context = context;

        // list = sectionTables.getSection();

    }

    public void updateListData(ArrayList<String> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PicturesAdapter.MyViewHolder holder, int position) {

        holder.image.setImageBitmap(getImageUri(position));


    }

    @Override
    public PicturesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_picutures_adapter, parent, false);
        return new PicturesAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        if (listData != null)
            return listData.size();
        else
            return 0;
    }

    public Bitmap getImageUri(int position) {
        File imgFile = new File(listData.get(position));

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
            // ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
            // myImage.setImageBitmap(myBitmap);
        } else {

            return null;

        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView image;


        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.imvSampleImage);
        }

        @Override
        public void onClick(View view) {


        }
    }
}
