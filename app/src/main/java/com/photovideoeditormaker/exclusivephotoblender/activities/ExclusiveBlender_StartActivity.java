package com.photovideoeditormaker.exclusivephotoblender.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.photovideoeditormaker.exclusivephotoblender.R;
import com.photovideoeditormaker.exclusivephotoblender.util.ExclusiveBlender_MyClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Process;


public class ExclusiveBlender_StartActivity extends AppCompatActivity {

    private static final String TAG = "ExclusiveBlender_StartActivity";
    public static Bitmap bitmap;
    private LinearLayout btnGallery;
    private LinearLayout btnMycreation;
    private RelativeLayout banner_layout;
    private NativeAdLayout nativeAdLayout;
    private AdView mAdView;
    private LinearLayout adView;
    private NativeAd nativeAd;
    String applicationName;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 88888;
    private com.google.android.gms.ads.InterstitialAd mInterstitialAdMob;
    private InterstitialAd interstitialAd;

    private com.google.android.gms.ads.InterstitialAd mInterstitialAdMobnext;
    private InterstitialAd interstitialAdnext;
    TextView starttxt, worktxt;
    ImageView logo;
    private com.facebook.ads.AdView adViewexit;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        bindView();
        showbanner();

        Typeface tf = Typeface.createFromAsset(getAssets(), "font7.ttf");

        starttxt = findViewById(R.id.starttxt);
        worktxt = findViewById(R.id.worktxt);
        logo = findViewById(R.id.logo);

        starttxt.setTypeface(tf, Typeface.BOLD);
        worktxt.setTypeface(tf, Typeface.BOLD);

        checkPermission_Glitch_cam();


        nativeAdLayout = findViewById(R.id.native_ad_container);
        nativeAdLayout.bringToFront();
        banner_layout = findViewById(R.id.banner_layout);
        if (isOnline()) {
            logo.setVisibility(View.GONE);
            banner_layout.setVisibility(View.GONE);
            loadNativeAd();

        } else {
            logo.setVisibility(View.VISIBLE);
            banner_layout.setVisibility(View.VISIBLE);
            nativeAdLayout.setVisibility(View.GONE);
        }

        applicationName = ExclusiveBlender_MyClass.Edit_Folder_name;

        File f = new File(Environment.getExternalStorageDirectory(), applicationName);
        if (!f.exists()) {
            f.mkdirs();
        }

        mInterstitialAdMob = new com.google.android.gms.ads.InterstitialAd(this);
        mInterstitialAdMob.setAdUnitId(getString(R.string.interstitial_full_screen_ad));
        mInterstitialAdMob.loadAd(new AdRequest.Builder().build());
        mInterstitialAdMob.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                mInterstitialAdMob.loadAd(new AdRequest.Builder().build());

                openGallery();
            }
        });


        mInterstitialAdMobnext = new com.google.android.gms.ads.InterstitialAd(this);
        mInterstitialAdMobnext.setAdUnitId(getString(R.string.interstitial_full_screen_ad));
        mInterstitialAdMobnext.loadAd(new AdRequest.Builder().build());
        mInterstitialAdMobnext.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                mInterstitialAdMobnext.loadAd(new AdRequest.Builder().build());

                startActivity(new Intent(getApplicationContext(), ExclusiveBlender_MyWorkActivity.class));
            }
        });

        interstitialAd = new InterstitialAd(this, getResources().getString(R.string.interstitial_full_screen_fb));
        interstitialAd.loadAd();
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                interstitialAd.loadAd();
                openGallery();
            }
        });

        interstitialAdnext = new InterstitialAd(this, getResources().getString(R.string.interstitial_full_screen_fb));
        interstitialAdnext.loadAd();
        interstitialAdnext.setAdListener(new InterstitialAdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                interstitialAdnext.loadAd();
                startActivity(new Intent(getApplicationContext(), ExclusiveBlender_MyWorkActivity.class));
            }
        });


    }

    private void showbanner() {
        MobileAds.initialize(this, getString(R.string.banner_footer));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void bindView() {
        btnGallery = (LinearLayout) findViewById(R.id.gallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                } else if (mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                } else {
                    openGallery();
                }
            }
        });
        btnMycreation = (LinearLayout) findViewById(R.id.btn_mycreation);
        btnMycreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAdnext.isAdLoaded()) {
                    interstitialAdnext.show();
                } else if (mInterstitialAdMobnext.isLoaded()) {
                    mInterstitialAdMobnext.show();
                } else {
                    startActivity(new Intent(getApplicationContext(), ExclusiveBlender_MyWorkActivity.class));
                }
            }
        });
    }

    private void moreapp() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(ExclusiveBlender_MyClass.acc_link)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "You don't have Google Play installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        startActivityForResult(new Intent(this, ExclusiveBlender_ExposureActivity.class), 1);

    }

    private void share() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.TEXT", ExclusiveBlender_MyClass.app_link);
        intent.putExtra("android.intent.extra.STREAM", Uri.parse(Media.insertImage(getContentResolver(), BitmapFactory.decodeResource(getResources(), R.drawable.banner), null, null)));
        startActivity(Intent.createChooser(intent, "Share Image using"));
    }

    private void showDialogOK(String str, OnClickListener onClickListener) {
        new AlertDialog.Builder(this).setMessage((CharSequence) str).setPositiveButton((CharSequence) "OK", onClickListener).setNegativeButton((CharSequence) "Cancel", onClickListener).create().show();
    }


    public void gotoStore() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "You don't have Google Play installed", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
        }
    }

    public void onBackPressed() {
//        super.onBackPressed();
        final Dialog dialog1 = new Dialog(ExclusiveBlender_StartActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog1.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        dialog1.setContentView(R.layout.exitdialog);

        dialog1.setCanceledOnTouchOutside(false);


        adViewexit = new com.facebook.ads.AdView(this, getString(R.string.medium_fb), AdSize.RECTANGLE_HEIGHT_250);
        LinearLayout adContainer = dialog1.findViewById(R.id.banner_containerexit);
        adContainer.addView(adViewexit);
        adViewexit.loadAd();

        TextView ad = dialog1.findViewById(R.id.ad);
        ad.bringToFront();

        LinearLayout cancel = dialog1.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        LinearLayout exit = dialog1.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO Auto-generated method stub
                dialog1.dismiss();
                moveTaskToBack(true);
                Process.killProcess(Process.myPid());
                System.exit(1);
            }
        });


        dialog1.show();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.rate:
                gotoStore();
                break;
            case R.id.share:
                if (VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                        if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 3);
                            break;
                        }
                    }
                    share();
                    break;
                }
                share();
                break;
            case R.id.more:
                if (!isOnline()) {
                    Toast.makeText(this, "No Internet Connection..", Toast.LENGTH_SHORT).show();
                    break;
                }
                moreapp();
                break;
            case R.id.privacy_policy:
                if (!isOnline()) {
                    Toast.makeText(this, "No Internet Connection..", Toast.LENGTH_SHORT).show();
                    break;
                }
                startActivity(new Intent(getApplicationContext(), ExclusiveBlender_WebActivity.class));
                break;
        }
        return true;
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onPostResume() {
        super.onPostResume();
    }


    protected void onRestart() {
        super.onRestart();
    }

    protected void onResume() {
        super.onResume();

    }

    private void loadNativeAd() {
        nativeAd = new NativeAd(this, getResources().getString(R.string.native_fb));
        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

        });
        nativeAd.loadAd();
    }

    private void inflateAd(NativeAd nativeAd) {
        nativeAd.unregisterView();
        nativeAdLayout = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(ExclusiveBlender_StartActivity.this);
        adView = (LinearLayout) inflater.inflate(R.layout.ad_unit, nativeAdLayout, false);
        nativeAdLayout.addView(adView);
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(ExclusiveBlender_StartActivity.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);
        AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView adtxt = adView.findViewById(R.id.adtxt);
        adtxt.bringToFront();
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeAd.registerViewForInteraction(
                adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);
    }

    protected void onStop() {
        super.onStop();
    }

    private boolean checkPermission_Glitch_cam() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
//                    Toast.makeText(ExclusiveBlender_StartActivity.this, "[WARN] camera permission is not grunted.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
