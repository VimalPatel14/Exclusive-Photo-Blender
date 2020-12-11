package com.photovideoeditormaker.exclusivephotoblender.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.photovideoeditormaker.exclusivephotoblender.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImage.ActivityResult;
import com.theartofdev.edmodo.cropper.CropImageView.Guidelines;

import java.io.IOException;

public class ExclusiveBlender_ExposureActivity extends Activity {

    static LinearLayout opacity;
    static LinearLayout backs;
    static Boolean f4422c = Boolean.valueOf(true);
    static Button delete;
    static ImageView done;
    static Bitmap f4425f;
    static LinearLayout camgal;
    static Boolean f4427h = Boolean.valueOf(true);
    static SeekBar seek;
    static SharedPreferences f4429j;
    static ImageView back;
    static RelativeLayout seek_rel;
    static ImageView f4432m = null;
    static Bitmap f4433n;
    static SeekBar sideblur_seek;
    static RelativeLayout tutrel;
    static RelativeLayout tutrel1;
    SharedPreferences f4437A;
    RelativeLayout rel;
    RelativeLayout rel1;
    int f4440D = 150;
    OnSeekBarChangeListener f4441E;
    SharedPreferences f4442r;
    Bitmap f4443s;

    LinearLayout bootomlay;
    RelativeLayout forcalrel;
    ImageView image;
    int[] f4449y = new int[]{R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5, R.drawable.bg_6, R.drawable.bg_7, R.drawable.bg_8, R.drawable.bg_9, R.drawable.bg_10, R.drawable.bg_11, R.drawable.bg_12, R.drawable.bg_13, R.drawable.bg_14, R.drawable.bg_15};
    ProgressBar progressBar;
    private AdView mAdView;
    private com.facebook.ads.AdView adView;
    private com.google.android.gms.ads.InterstitialAd mInterstitialAdMob, mInterstitialAdMobgallery;
    private InterstitialAd interstitialAd, interstitialAdgallery;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.love_photo_blender);

        showbanner();


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
                startActivity(new Intent(ExclusiveBlender_ExposureActivity.this, ExclusiveBlender_EditingActivity.class));

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
                startActivity(new Intent(ExclusiveBlender_ExposureActivity.this, ExclusiveBlender_EditingActivity.class));
            }
        });


        mInterstitialAdMobgallery = new com.google.android.gms.ads.InterstitialAd(this);
        mInterstitialAdMobgallery.setAdUnitId(getString(R.string.interstitial_full_screen_ad));
        mInterstitialAdMobgallery.loadAd(new AdRequest.Builder().build());
        mInterstitialAdMobgallery.setAdListener(new com.google.android.gms.ads.AdListener() {
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
                mInterstitialAdMobgallery.loadAd(new AdRequest.Builder().build());
                startActivity(new Intent(ExclusiveBlender_ExposureActivity.this, ExclusiveBlender_BackgroundActivity.class));

            }
        });

        interstitialAdgallery = new InterstitialAd(this, getResources().getString(R.string.interstitial_full_screen_fb));
        interstitialAdgallery.loadAd();
        interstitialAdgallery.setAdListener(new InterstitialAdListener() {
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
                interstitialAdgallery.loadAd();
                startActivity(new Intent(ExclusiveBlender_ExposureActivity.this, ExclusiveBlender_BackgroundActivity.class));
            }
        });


        progressBar = findViewById(R.id.progressBar);
        image = findViewById(R.id.image);
        camgal = findViewById(R.id.camgal);
        backs = findViewById(R.id.backs);
        opacity = findViewById(R.id.opacity);
        done = findViewById(R.id.done);
        delete = findViewById(R.id.delete);
        back = findViewById(R.id.startover);
        forcalrel = findViewById(R.id.forcalrel);
        bootomlay = findViewById(R.id.bootomlay);
        bootomlay.setVisibility(View.INVISIBLE);
        seek = findViewById(R.id.seek);
        seek.setMax(255);
        seek.setProgress(125);
        sideblur_seek = findViewById(R.id.sideblur_seek);
        sideblur_seek.setMax(255);
        sideblur_seek.setProgress(f4440D);
        rel = findViewById(R.id.rel);
        rel1 = findViewById(R.id.rel1);
        seek_rel = findViewById(R.id.seek_rel);
        tutrel = findViewById(R.id.tutrel);
        tutrel1 = findViewById(R.id.tutrel1);
        bootomlay.setVisibility(View.VISIBLE);
        f4437A = PreferenceManager.getDefaultSharedPreferences(this);
        f4442r = PreferenceManager.getDefaultSharedPreferences(this);
        f4429j = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        image.setBackgroundResource(R.drawable.bg_1);
        rel.setLayoutParams(new LayoutParams(-1, -1));
        rel.setBackgroundColor(0);
        camgal.setOnClickListener(new C10271());
        backs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAdMobgallery.isLoaded()) {
                    mInterstitialAdMobgallery.show();
                } else if (interstitialAdgallery.isAdLoaded()) {
                    interstitialAdgallery.show();
                } else {
                    startActivity(new Intent(ExclusiveBlender_ExposureActivity.this, ExclusiveBlender_BackgroundActivity.class));
                }
            }
        });
        opacity.setOnClickListener(new C10293());
        seek.setOnSeekBarChangeListener(new C10324());
        SeekBar seekBar = sideblur_seek;
        C10355 c10355 = new C10355();
        f4441E = c10355;
        seekBar.setOnSeekBarChangeListener(c10355);
        back.setOnClickListener(new C10386());
        delete.setOnClickListener(new C10397());
        done.setOnClickListener(new C10408());
    }


    private void showbanner() {
        MobileAds.initialize(this, getString(R.string.banner_footer));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    class C10271 implements OnClickListener {
        C10271() {
        }

        public void onClick(View view) {
            ExclusiveBlender_ExposureActivity.seek_rel.setVisibility(View.GONE);
            ExclusiveBlender_ExposureActivity.f4427h = Boolean.valueOf(true);
            ExclusiveBlender_ExposureActivity.f4422c = Boolean.valueOf(true);
            CropImage.activity(null).setGuidelines(Guidelines.ON).start(ExclusiveBlender_ExposureActivity.this);

        }
    }


    class C10293 implements OnClickListener {
        C10293() {
        }

        public void onClick(View view) {
            if (ExclusiveBlender_ExposureActivity.f4432m == null) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.selectim).toString(), Toast.LENGTH_SHORT).show();
            } else if (ExclusiveBlender_ExposureActivity.f4427h.booleanValue()) {
                ExclusiveBlender_ExposureActivity.seek_rel.setVisibility(View.VISIBLE);
                ExclusiveBlender_ExposureActivity.f4427h = Boolean.valueOf(false);
                ExclusiveBlender_ExposureActivity.f4422c = Boolean.valueOf(true);
            } else {
                ExclusiveBlender_ExposureActivity.seek_rel.setVisibility(View.GONE);
                ExclusiveBlender_ExposureActivity.f4427h = Boolean.valueOf(true);
            }
        }
    }

    class C10324 implements OnSeekBarChangeListener {

        class C10311 implements OnTouchListener {

            class C10301 implements OnTouchListener {
                C10301() {
                }

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    ExclusiveBlender_ExposureActivity.tutrel1.setVisibility(View.GONE);
                    return true;
                }
            }

            C10311() {
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                ExclusiveBlender_ExposureActivity.tutrel.setVisibility(View.GONE);
                ExclusiveBlender_ExposureActivity.tutrel1.setVisibility(View.VISIBLE);
                ExclusiveBlender_ExposureActivity.tutrel1.setOnTouchListener(new C10301());
                return true;
            }
        }

        C10324() {
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            int max = ExclusiveBlender_ExposureActivity.seek.getMax() - i;
            if (VERSION.SDK_INT <= 16) {
                ExclusiveBlender_ExposureActivity.f4432m.setAlpha(max);
            } else {
                ExclusiveBlender_ExposureActivity.f4432m.setImageAlpha(max);
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            ExclusiveBlender_ExposureActivity.tutrel.setOnTouchListener(new C10311());
        }
    }

    class C10355 implements OnSeekBarChangeListener {

        class C10341 implements OnTouchListener {

            class C10331 implements OnTouchListener {
                C10331() {
                }

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    ExclusiveBlender_ExposureActivity.tutrel1.setVisibility(View.GONE);
                    return true;
                }
            }

            C10341() {
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                ExclusiveBlender_ExposureActivity.tutrel.setVisibility(View.GONE);
                ExclusiveBlender_ExposureActivity.tutrel1.setVisibility(View.VISIBLE);
                ExclusiveBlender_ExposureActivity.tutrel1.setOnTouchListener(new C10331());
                return true;
            }
        }

        C10355() {
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (i == 0) {
                ExclusiveBlender_ExposureActivity.f4432m.setImageBitmap(mo14177a(ExclusiveBlender_ExposureActivity.f4433n, 1));
                ExclusiveBlender_ExposureActivity.f4432m.setContentDescription("1");
                f4440D = 1;
                return;
            }
            ExclusiveBlender_ExposureActivity.f4432m.setImageBitmap(mo14177a(ExclusiveBlender_ExposureActivity.f4433n, i));
            ExclusiveBlender_ExposureActivity.f4432m.setContentDescription(i + "");
            f4440D = i;
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            ExclusiveBlender_ExposureActivity.tutrel.setOnTouchListener(new C10341());
        }
    }

    class C10386 implements OnClickListener {

        class C10361 implements DialogInterface.OnClickListener {
            /* renamed from: a */


            public void onClick(DialogInterface dialogInterface, int i) {
                ExclusiveBlender_ExposureActivity.seek_rel.setVisibility(View.GONE);
                ExclusiveBlender_ExposureActivity.f4427h = Boolean.valueOf(true);
                ExclusiveBlender_ExposureActivity.f4422c = Boolean.valueOf(true);
                ExclusiveBlender_ExposureActivity.delete.setVisibility(View.INVISIBLE);
                rel1.removeAllViews();
                image.setImageBitmap(null);
                rel.setBackgroundColor(0);
                ExclusiveBlender_ExposureActivity.f4432m = null;
                dialogInterface.cancel();
            }
        }

        class C10372 implements DialogInterface.OnClickListener {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }

        C10386() {
        }

        public void onClick(View view) {
            finish();
        }
    }

    class C10397 implements OnClickListener {
        C10397() {
        }

        public void onClick(View view) {
            if (ExclusiveBlender_ExposureActivity.f4432m != null) {
                ExclusiveBlender_ExposureActivity.f4432m.setImageBitmap(null);
                RelativeLayout relativeLayout = (RelativeLayout) ExclusiveBlender_ExposureActivity.f4432m.getParent();
                relativeLayout.removeAllViews();
                relativeLayout.setOnTouchListener(null);
                ExclusiveBlender_ExposureActivity.f4432m = null;
                ExclusiveBlender_ExposureActivity.delete.setVisibility(View.INVISIBLE);
                ExclusiveBlender_ExposureActivity.seek_rel.setVisibility(View.GONE);
                ExclusiveBlender_ExposureActivity.f4427h = Boolean.valueOf(true);
            }
        }
    }

    class C10408 implements OnClickListener {
        C10408() {
        }

        public void onClick(View view) {
            if (ExclusiveBlender_ExposureActivity.f4432m == null) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.selectim).toString(), Toast.LENGTH_SHORT).show();
                return;
            }
            ExclusiveBlender_ExposureActivity.f4425f = null;
            save_mg();
        }
    }

    private void imageFromGallery(Uri uri) {
        try {
            f4443s = Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            f4433n = Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addImage(f4443s);
        if (f4429j.getInt("tut", 0) == 1) {
            backs.setEnabled(false);
            camgal.setEnabled(false);
            opacity.setEnabled(true);
            done.setEnabled(false);
            delete.setEnabled(false);
            back.setEnabled(false);
            Editor edit = f4429j.edit();
            edit.putInt("tut", 2);
            edit.commit();
        }
    }


    Bitmap mo14176a(Bitmap bitmap) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float f = (float) displayMetrics.widthPixels;
        float height = ((float) displayMetrics.heightPixels) - ((float) forcalrel.getHeight());
        float width = (float) bitmap.getWidth();
        float height2 = (float) bitmap.getHeight();
        float f2 = width / height2;
        float f3 = height2 / width;
        if (width > f) {
            height = f * f3;
        } else if (height2 > height) {
            f = height * f2;
        } else if (f2 > 0.75f) {
            height = f * f3;
        } else if (f3 > 1.5f) {
            f = height * f2;
        } else {
            height = f * f3;
        }
        return Bitmap.createScaledBitmap(bitmap, (int) f, (int) height, false);
    }


    Bitmap mo14177a(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setMaskFilter(new BlurMaskFilter((float) i, Blur.NORMAL));
        Path path = new Path();
        path.moveTo((float) i, (float) i);
        path.lineTo((float) (canvas.getWidth() - i), (float) i);
        path.lineTo((float) (canvas.getWidth() - i), (float) (canvas.getHeight() - i));
        path.lineTo((float) i, (float) (canvas.getHeight() - i));
        path.lineTo((float) i, (float) i);
        path.close();
        canvas.drawPath(path, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public void addImage(Bitmap bitmap) {
        Bitmap a = mo14176a(bitmap);
        RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageBitmap(a);
        imageView.setVisibility(View.INVISIBLE);
        relativeLayout.addView(imageView);
        ImageView imageView2 = new ImageView(getApplicationContext());
        sideblur_seek.setOnSeekBarChangeListener(null);
        int width = a.getWidth();
        int height = a.getHeight();
        if (height < width) {
            sideblur_seek.setMax(height / 3);
            sideblur_seek.setProgress((height / 3) / 2);
            f4440D = (height / 3) / 2;
        } else if (width < height) {
            sideblur_seek.setMax(width / 3);
            sideblur_seek.setProgress((width / 3) / 2);
            f4440D = (width / 3) / 2;
        } else {
            sideblur_seek.setMax(width / 3);
            sideblur_seek.setProgress((width / 3) / 2);
            f4440D = (width / 3) / 2;
        }
        imageView2.setImageBitmap(mo14177a(a, f4440D));
        imageView2.setContentDescription(f4440D + "");
        if (VERSION.SDK_INT <= 16) {
            imageView2.setAlpha(125);
        } else {
            imageView2.setImageAlpha(125);
        }
        relativeLayout.addView(imageView2);
        imageView.setVisibility(View.INVISIBLE);
        sideblur_seek.setOnSeekBarChangeListener(f4441E);
        relativeLayout.setOnTouchListener(new ExclusiveBlender_MultiTouchListener());
        rel1.addView(relativeLayout);
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && intent != null) {
            switch (i) {
                case 1:
                    CropImage.activity(intent.getData()).start(this);

                    return;
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE /*203*/:
                    ActivityResult activityResult = CropImage.getActivityResult(intent);
                    if (i2 == -1) {
                        imageFromGallery(activityResult.getUri());

                        return;
                    } else if (i2 == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        activityResult.getError();
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ExclusiveBlender_ExposureActivity.this, ExclusiveBlender_StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }


    protected void onResume() {
        super.onResume();
        image.setBackgroundResource(f4449y[ExclusiveBlender_BackgroundActivity.collage_id]);
        rel.setLayoutParams(new LayoutParams(-1, -1));
        rel.setBackgroundColor(0);

    }

    public void save_mg() {
        seek_rel.setVisibility(View.GONE);
        f4427h = Boolean.valueOf(true);
        rel.setDrawingCacheEnabled(true);
        Bitmap createBitmap = Bitmap.createBitmap(rel.getDrawingCache());
        rel.setDrawingCacheEnabled(false);
        f4425f = mo14176a(createBitmap);

        if (interstitialAd.isAdLoaded()) {
            interstitialAd.show();
        } else if (mInterstitialAdMob.isLoaded()) {
            mInterstitialAdMob.show();
        } else {
            startActivity(new Intent(this, ExclusiveBlender_EditingActivity.class));
        }

    }


}
