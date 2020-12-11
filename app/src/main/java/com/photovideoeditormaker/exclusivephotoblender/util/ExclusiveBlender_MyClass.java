package com.photovideoeditormaker.exclusivephotoblender.util;

import android.os.Environment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class ExclusiveBlender_MyClass {

    public static String Edit_Folder_name = "Exclusive Photo Blender Effect";
    public static ArrayList<String> IMAGEALLARY = new ArrayList();
    public static String acc_link = "https://play.google.com/store/apps/developer?id=Photo+Video+Editor+Maker";
    public static String app_link = "https://play.google.com/store/apps/details?id=com.photovideoeditormaker.exclusivephotoblender&hl=en";
    public static String app_name = "Exclusive Photo Blender Effect";
    public static String privacy_link = "https://photovideoeditormaker.blogspot.com/p/privacy-policy.html";

    public static boolean createDirIfNotExists(String str) {
        File file = new File(Environment.getExternalStorageDirectory(), "/" + str);
        if (file.exists()) {
            System.out.println("Can't create folder");
            return true;
        }
        file.mkdir();
        return file.mkdirs();
    }

    public static void listAllImages(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (int length = listFiles.length - 1; length >= 0; length--) {
                String file2 = listFiles[length].toString();
                File file3 = new File(file2);
                Log.d("" + file3.length(), "" + file3.length());
                if (file3.length() <= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
                    Log.i("Invalid Image", "Delete Image");
                } else if (file3.toString().contains(".jpg") || file3.toString().contains(".png") || file3.toString().contains(".jpeg")) {
                    IMAGEALLARY.add(file2);
                }
                System.out.println(file2);
            }
            return;
        }
        System.out.println("Empty Folder");
    }
}
