package com.photovideoeditormaker.exclusivephotoblender.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.photovideoeditormaker.exclusivephotoblender.adapters.ExclusiveBlender_WorkAdapter;
import com.photovideoeditormaker.exclusivephotoblender.R;
import com.photovideoeditormaker.exclusivephotoblender.util.ExclusiveBlender_MyClass;

import java.io.File;
import java.util.Collections;

public class ExclusiveBlender_MyWorkActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 5;
    private ImageView bback;
    private ExclusiveBlender_WorkAdapter galleryAdapter;
    private GridView lstList;
    private LinearLayout native_ad_container;
    private ImageView noImage;
    private ImageView noimg;
    private com.facebook.ads.AdView adView;
    private AdView mAdView;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_creation);
        bindView();
        showfbbanner();
        showbanner();
    }

    private void bindView() {
        this.noImage =  findViewById(R.id.novideoimg);
        this.lstList = findViewById(R.id.lstList);
        getImages();
        if (ExclusiveBlender_MyClass.IMAGEALLARY.size() <= 0) {
            this.noImage.setVisibility(View.VISIBLE);
            this.lstList.setVisibility(View.GONE);
        } else {
            this.noImage.setVisibility(View.GONE);
            this.lstList.setVisibility(View.VISIBLE);
        }
        this.bback =  findViewById(R.id.back);
        this.bback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Collections.sort(ExclusiveBlender_MyClass.IMAGEALLARY);
        Collections.reverse(ExclusiveBlender_MyClass.IMAGEALLARY);
        this.galleryAdapter = new ExclusiveBlender_WorkAdapter(this, ExclusiveBlender_MyClass.IMAGEALLARY);
        this.lstList.setAdapter(this.galleryAdapter);
    }

    private void fetchImage() {
        ExclusiveBlender_MyClass.IMAGEALLARY.clear();
        ExclusiveBlender_MyClass.listAllImages(new File("/mnt/sdcard/" + ExclusiveBlender_MyClass.Edit_Folder_name + "/"));
    }

    private void getImages() {
        if (VERSION.SDK_INT < 23) {
            fetchImage();
        } else if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            fetchImage();
        } else if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 5);
        }
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }




    @TargetApi(23)
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        switch (i) {
            case 5:
                if (iArr[0] == 0) {
                    fetchImage();
                    return;
                } else if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 5);
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    protected void onResume() {
        super.onResume();

    }

    private void showfbbanner() {
        adView = new com.facebook.ads.AdView(this, getString(R.string.banner_ad_fb), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
    }


    private void showbanner() {
        MobileAds.initialize(this, getString(R.string.banner_footer));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ExclusiveBlender_MyWorkActivity.this, ExclusiveBlender_StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
