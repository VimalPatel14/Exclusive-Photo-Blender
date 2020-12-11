package com.photovideoeditormaker.exclusivephotoblender.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.photovideoeditormaker.exclusivephotoblender.BuildConfig;
import com.photovideoeditormaker.exclusivephotoblender.R;
import com.photovideoeditormaker.exclusivephotoblender.util.ExclusiveBlender_MyClass;

import java.io.File;

public class ExclusiveBlender_ShareImageActivity extends AppCompatActivity {

    ImageView back, delete, share, previewimage;
    private String imgPath = "share";
    ProgressDialog pd = null;
    private com.facebook.ads.AdView adView;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);

        showfbbanner();
        showbanner();

        back = findViewById(R.id.back);
        delete = findViewById(R.id.delete);
        share = findViewById(R.id.share);
        previewimage = findViewById(R.id.previewimage);

        Bundle bundle = getIntent().getExtras();
        imgPath = bundle.getString("imgPath");

        Log.e("imgPath", imgPath);

        if (imgPath != null) {
            previewimage.setImageURI(Uri.parse(imgPath));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(ExclusiveBlender_ShareImageActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File(String.valueOf(imgPath)));

                Intent shareIntent = new Intent("android.intent.action.SEND");
                shareIntent.setType("video/*");
                shareIntent.putExtra("android.intent.extra.SUBJECT", new File(String.valueOf(uri)));
                shareIntent.putExtra("android.intent.extra.TITLE", new File(String.valueOf(uri)));
                shareIntent.putExtra("android.intent.extra.STREAM", uri);
                shareIntent.putExtra("android.intent.extra.TEXT", ExclusiveBlender_MyClass.app_name + " Create By : " + ExclusiveBlender_MyClass.app_link);
                startActivity(Intent.createChooser(shareIntent, "Share Video"));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dial = new Dialog(ExclusiveBlender_ShareImageActivity.this, 16973839);
                dial.requestWindowFeature(1);
                dial.setContentView(R.layout.delete_confirmation);
                dial.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dial.setCanceledOnTouchOutside(true);
                ((TextView) dial.findViewById(R.id.delete_yes)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        File appmusic = new File(imgPath);
                        if (appmusic.exists()) {
                            try {
                                appmusic.delete();
                                MediaScannerConnection.scanFile(
                                        ExclusiveBlender_ShareImageActivity.this,
                                        new String[]{imgPath}, null,
                                        null);

                                DeleteVideoFromMediaStore(
                                        getApplicationContext(), imgPath);
                            } catch (Exception e) {
                            }
                        }
                        pd = new ProgressDialog(ExclusiveBlender_ShareImageActivity.this,
                                R.style.AppDialogTheme1);
                        pd.setMessage("Deleting Image...");
                        pd.setCancelable(false);
                        pd.show();
                        dial.dismiss();

                        Intent a = new Intent(ExclusiveBlender_ShareImageActivity.this, ExclusiveBlender_MyWorkActivity.class);
                        startActivity(a);

                    }
                });
                ((TextView) dial.findViewById(R.id.delete_no)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dial.dismiss();
                    }
                });
                dial.show();
            }
        });
    }


    public void DeleteVideoFromMediaStore(Context context, String path) {
        Uri rootUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        context.getContentResolver().delete(rootUri,
                MediaStore.MediaColumns.DATA + "=?", new String[]{path});
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
        Intent intent = new Intent(ExclusiveBlender_ShareImageActivity.this, ExclusiveBlender_MyWorkActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
