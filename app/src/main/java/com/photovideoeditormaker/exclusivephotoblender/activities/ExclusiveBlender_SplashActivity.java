package com.photovideoeditormaker.exclusivephotoblender.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.ads.AdSettings;
import com.google.android.gms.ads.AdRequest;
import com.photovideoeditormaker.exclusivephotoblender.R;

public class ExclusiveBlender_SplashActivity extends Activity {

    private com.google.android.gms.ads.InterstitialAd mInterstitialAdMob;
    private static int SPLASH_TIME_OUT = 5000;

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        AdSettings.addTestDevice(getResources().getString(R.string.device_id));

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
                Intent i = new Intent(ExclusiveBlender_SplashActivity.this, ExclusiveBlender_StartActivity.class);
                startActivity(i);
                finish();

            }
        });

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                } else {
                    startActivity(new Intent(ExclusiveBlender_SplashActivity.this, ExclusiveBlender_StartActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }

}
