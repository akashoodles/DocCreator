package com.example.akash.demoapplication.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by akash on 15/12/17.
 */

public  class FileUtils {
    private static final String IMAGE_DIRECTORY_NAME = "Image Converter";
    public static File getOutputMediaFileForPicture() {
        // External sdcard location
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




        return mediaStorageDir;
    }
    public static File getOutputMediaFileForPdf() {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        return mediaStorageDir;
    }
    public static void deletePicute()
    {
        String[]entries = FileUtils.getOutputMediaFileForPicture().list();
        for(String s: entries){
            File currentFile = new File(FileUtils.getOutputMediaFileForPicture().getPath(),s);
            currentFile.delete();
        }
    }

}