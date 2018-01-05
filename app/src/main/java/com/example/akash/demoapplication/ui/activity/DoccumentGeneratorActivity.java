package com.example.akash.demoapplication.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.akash.demoapplication.R;
import com.example.akash.demoapplication.constant.AppConstant;
import com.example.akash.demoapplication.ui.adapter.PicturesAdapter;
import com.example.akash.demoapplication.utils.FileUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DoccumentGeneratorActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private PicturesAdapter adapter;
    private static final String IMAGE_DIRECTORY_NAME = "Image Converter";
    private Button btngenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_pictures);
        adapter = new PicturesAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.rcySamplePic);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.updateListData(getAllMediaPath());
        btngenerate = (Button) findViewById(R.id.btnGenerate);
        btngenerate.setText(getResources().getStringArray(R.array.category)[getIntent().getIntExtra(AppConstant.SOURCE, 0)]);
        btngenerate.setOnClickListener(this);
//        File imgFile = new  File("/sdcard/Images/test_image.jpg");
//
//        if(imgFile.exists()){
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//            ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
//
//            myImage.setImageBitmap(myBitmap);
//
//        }
    }

    private ArrayList<String> getAllMediaPath() {
        ArrayList<String> listFilePath = new ArrayList<>();
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        for (int i = 0; i < mediaStorageDir.listFiles().length; i++)

        {
            listFilePath.add(mediaStorageDir.listFiles()[i].getAbsolutePath());
            Log.e("Sample Activity ", mediaStorageDir.listFiles()[i].getAbsolutePath());

        }
        return listFilePath;

    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Log.e("create pfd", ".......");
        Image img = Image.getInstance(getAllMediaPath().get(0));
        Document document = new Document(PageSize.A4);


       // document.setMargins(0, 0,200, 0);
        PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        document.setMargins(0,0,200,0);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 25, Font.BOLD, BaseColor.BLACK);
        Chunk c = new Chunk("  Created by " + "\n" + "Document Creator \npowered by Oodles technologies pvt ltd.", f);
        Paragraph p = new Paragraph(c);
        p.setAlignment(Element.ALIGN_CENTER);

//        PdfContentByte canvas = writer.getDirectContentUnder();
//        InputStream ims = getAssets().open("logo.jpg");
//        Bitmap bmp = BitmapFactory.decodeStream(ims);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        Image img1 = Image.getInstance(stream.toByteArray());
//        img1.scalePercent(200f);
//        img1.setAlignment(Element.ALIGN_TOP);
//        img1.setAbsolutePosition(0, 0);
//        canvas.addImage(img1);
//
        Paragraph p2 = new Paragraph();
        InputStream ims = getAssets().open("logo.jpg");
        Bitmap bmp = BitmapFactory.decodeStream(ims);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img1 = Image.getInstance(stream.toByteArray());
        img1.scalePercent(100f);
        img1.setAlignment(Image.ALIGN_CENTER | Image.TEXTWRAP);
        // Notice the image added directly to the Paragraph
        p2.add(img1);
        p.setSpacingBefore(document.getPageSize().getHeight()/4);
        document.add(p2);
        document.add(p);
        document.newPage();
        for (String image : getAllMediaPath()) {
            img = Image.getInstance(image);
            document.newPage();
            document.setPageSize(PageSize.A4);
            document.setMargins(0, 0, 0, 0);
            img.scaleAbsolute(PageSize.A4);
            img.setAbsolutePosition(0, 0) ;
            document.add(img);
        }
        document.close();
        FileUtils.deletePicute();
      setResult(AppConstant.RES_CAMERAACTIVITY);
      finish();

        Log.e("complete pfd", ".......");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnGenerate:
                try {
                    createPdf(getOutputMediaFile());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                break;
        }

    }


    private String getOutputMediaFile() {
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(FileUtils.getOutputMediaFileForPdf().getPath() + File.separator
                + "PDF_" + timeStamp + ".pdf");
        return mediaFile.getAbsolutePath();
    }


}

