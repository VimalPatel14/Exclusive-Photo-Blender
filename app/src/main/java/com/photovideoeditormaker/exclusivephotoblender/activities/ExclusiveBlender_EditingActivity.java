package com.photovideoeditormaker.exclusivephotoblender.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.photovideoeditormaker.exclusivephotoblender.R;
import com.photovideoeditormaker.exclusivephotoblender.adapters.ExclusiveBlender_FontAdapter;
import com.photovideoeditormaker.exclusivephotoblender.adapters.ExclusiveBlender_StickerAdapter;
import com.photovideoeditormaker.exclusivephotoblender.util.ExclusiveBlender_Effect;
import com.photovideoeditormaker.exclusivephotoblender.util.ExclusiveBlender_MyClass;
import com.photovideoeditormaker.exclusivephotoblender.util.ExclusiveBlender_Utils;
import com.photovideoeditormaker.exclusivephotoblender.view.ExclusiveBlender_StickerView;
import com.photovideoeditormaker.exclusivephotoblender.view.ExclusiveBlender_StickerView.OperationListener;
import com.photovideoeditormaker.exclusivephotoblender.view.ExclusiveBlender_TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ExclusiveBlender_EditingActivity extends AppCompatActivity implements OnClickListener {
    private static final int FINAL_SAVE = 3;
    private static final int MY_REQUEST_CODE = 2;
    private static final int MY_REQUEST_CODE2 = 5;
    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 4;
    public static String _uri2;
    public static Bitmap bmp;
    public static Bitmap bitmap;
    public static Canvas f4477c;
    private static int columnWidth = 80;
    public static Bitmap finalEditedImage;
    public static ExclusiveBlender_TextView mCurrentTextView;
    public static Uri selectedUri;
    public static Bitmap textBitmap;
    public static String urlForShareImage;
    private ImageView Img_light;
    private ImageView back;
    private Context context;
    private Dialog dialog;
    private Bitmap editedBitmap;
    private EditText edittext;
    private ImageView ef1;
    private ImageView ef10;
    private ImageView ef11;
    private ImageView ef12;
    private ImageView ef13;
    private ImageView ef14;
    private ImageView ef15;
    private ImageView ef16;
    private ImageView ef17;
    private ImageView ef18;
    private ImageView ef19;
    private ImageView ef2;
    private ImageView ef20;
    private ImageView ef21;
    private ImageView ef22;
    private ImageView ef3;
    private ImageView ef4;
    private ImageView ef5;
    private ImageView ef6;
    private ImageView ef7;
    private ImageView ef8;
    private ImageView ef9;
    private ImageView ef_original;
    private FrameLayout fl_Sticker;
    private boolean flagForFlip = true;
    private boolean flagforcollage = false;
    private BaseAdapter frameAdapter;
    private int frmId = 0;
    private ArrayList<Integer> frmList;
    private int initColor;
    private boolean isBrightness = true;
    private boolean isImgSelected = true;
    private boolean isLight = false;
    private ImageView iv_gravity;
    private LinearLayout llFilter;
    private LinearLayout llFlip;
    private LinearLayout llRotate;
    private LinearLayout llSticker;
    private LinearLayout llText;
    private LinearLayout ll_Effecs_Panel;
    private LinearLayout ll_detail;
    private LinearLayout ll_effect_list;
    private LinearLayout lycolorlist;
    private LinearLayout lyfontlist;
    private ExclusiveBlender_StickerView mCurrentView;
    private int mPickedColor = -1;
    private ArrayList<View> mStickers;
    private ArrayList<View> mViews;
    public float[] mainMatrix = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private ImageView orgImage;
    InputMethodManager f4478p;
    ImageView iv_color;
    GridView gvcolorlist;
    private int rotate = 1;
    GridView gvfontlist;
    private ImageView save;
    private SeekBar seekBrightness;
    private ExclusiveBlender_StickerAdapter exclusiveBlenderStickerAdapter;
    private int stickerId;
    private ArrayList<Integer> stickerList = new ArrayList();
    private ArrayList<Integer> stickerlist;
    String[] f4482t = new String[]{"font1.ttf", "font2.ttf", "font3.ttf", "font4.ttf", "font5.ttf", "font6.ttf", "font7.ttf", "font8.ttf", "font9.ttf", "font11.ttf", "font12.ttf", "font13.ttf", "font49.ttf", "font50.ttf"};
    private Typeface type;
    ImageView iv_keyboard;
    ImageView iv_fontstyle;
    private int view_id;
    private int f4485w = 0;
    ImageView iv_done;
    FrameLayout frm_Main;
    private AdView mAdView;
    private com.facebook.ads.AdView adView;
    String applicationName;
    String path;
    File file;
    Bitmap selectedImg;
    private Uri mImageSavedUri;
    private com.google.android.gms.ads.InterstitialAd mInterstitialAdMob;
    private InterstitialAd interstitialAd;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initColor = getResources().getColor(R.color.white);
        setContentView((int) R.layout.activity_image_editing);
        mViews = new ArrayList();
        mStickers = new ArrayList();
        bindView();
        context = this;
        setArraylistForSticker();
        createDir();
        showbanner();


        applicationName = getResources().getString(R.string.folder_name);


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
                new saveImage().execute();

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
                new saveImage().execute();
            }
        });


    }

    private void showbanner() {
        MobileAds.initialize(this, getString(R.string.banner_footer));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    class C102410 implements OnClickListener {
        C102410() {
        }

        public void onClick(View view) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(edittext.getWindowToken(), 0);
            lycolorlist.setVisibility(View.VISIBLE);
            lyfontlist.setVisibility(View.GONE);
        }
    }

    class C10311 implements OnSeekBarChangeListener {
        C10311() {
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            setBlackAndWhite(orgImage, i + 100);
            seekBrightness.setVisibility(View.VISIBLE);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

   

    class C10333 implements OnClickListener {
        C10333() {
        }

        public void onClick(View view) {
            if (mCurrentView != null) {
                mCurrentView.setInEdit(false);
            }
            if (ExclusiveBlender_EditingActivity.mCurrentTextView != null) {
                ExclusiveBlender_EditingActivity.mCurrentTextView.setInEdit(false);
            }
        }
    }


    class C10388 implements OnClickListener {
        C10388() {
        }

        public void onClick(View view) {
            ((InputMethodManager) getSystemService("input_method")).showSoftInput(edittext, 2);
            lyfontlist.setVisibility(View.GONE);
            lycolorlist.setVisibility(View.GONE);
        }
    }

    class C10399 implements OnClickListener {
        C10399() {
        }

        public void onClick(View view) {
            lyfontlist.setVisibility(View.VISIBLE);
            lycolorlist.setVisibility(View.GONE);
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(edittext.getWindowToken(), 0);
        }
    }

    public static int HSVColor(float f, float f2, float f3) {
        return Color.HSVToColor(255, new float[]{f, f2, f3});
    }

    public static ArrayList HSVColors() {
        int i;
        int i2 = 0;
        ArrayList arrayList = new ArrayList();
        for (i = 0; i <= 360; i += 20) {
            arrayList.add(Integer.valueOf(HSVColor((float) i, 1.0f, 1.0f)));
        }
        for (i = 0; i <= 360; i += 20) {
            arrayList.add(Integer.valueOf(HSVColor((float) i, 0.25f, 1.0f)));
            arrayList.add(Integer.valueOf(HSVColor((float) i, 0.5f, 1.0f)));
            arrayList.add(Integer.valueOf(HSVColor((float) i, 0.75f, 1.0f)));
        }
        while (i2 <= 360) {
            arrayList.add(Integer.valueOf(HSVColor((float) i2, 1.0f, 0.5f)));
            arrayList.add(Integer.valueOf(HSVColor((float) i2, 1.0f, 0.75f)));
            i2 += 20;
        }
        for (float f = 0.0f; f <= 1.0f; f += 0.1f) {
            arrayList.add(Integer.valueOf(HSVColor(0.0f, 0.0f, f)));
        }
        return arrayList;
    }

    private void addStickerView(int i) {
        final ExclusiveBlender_StickerView exclusiveBlenderStickerView = new ExclusiveBlender_StickerView(this);
        exclusiveBlenderStickerView.setImageResource(i);
        exclusiveBlenderStickerView.setOperationListener(new OperationListener() {
            public void onDeleteClick() {
                mViews.remove(exclusiveBlenderStickerView);
                fl_Sticker.removeView(exclusiveBlenderStickerView);
            }

            public void onEdit(ExclusiveBlender_StickerView exclusiveBlenderStickerView) {
                mCurrentView.setInEdit(false);
                mCurrentView = exclusiveBlenderStickerView;
                mCurrentView.setInEdit(true);
            }

            public void onTop(ExclusiveBlender_StickerView exclusiveBlenderStickerView) {
                int indexOf = mViews.indexOf(exclusiveBlenderStickerView);
                if (indexOf != mViews.size() - 1) {
                    mViews.add(mViews.size(), (ExclusiveBlender_StickerView) mViews.remove(indexOf));
                }
            }
        });
        fl_Sticker.addView(exclusiveBlenderStickerView, new LayoutParams(-1, -1));
        mViews.add(exclusiveBlenderStickerView);
        setCurrentEdit(exclusiveBlenderStickerView);
    }

    private void bindEffectIcon() {
        ef_original = findViewById(R.id.ef_original);
        ef_original.setOnClickListener(this);
        ef1 = findViewById(R.id.ef1);
        ef1.setOnClickListener(this);
        ef2 = findViewById(R.id.ef2);
        ef2.setOnClickListener(this);
        ef3 = findViewById(R.id.ef3);
        ef3.setOnClickListener(this);
        ef4 = findViewById(R.id.ef4);
        ef4.setOnClickListener(this);
        ef5 = findViewById(R.id.ef5);
        ef5.setOnClickListener(this);
        ef6 = findViewById(R.id.ef6);
        ef6.setOnClickListener(this);
        ef9 = findViewById(R.id.ef9);
        ef9.setOnClickListener(this);
        ef10 = findViewById(R.id.ef10);
        ef10.setOnClickListener(this);
        ef11 = findViewById(R.id.ef11);
        ef11.setOnClickListener(this);
        ef12 = findViewById(R.id.ef12);
        ef12.setOnClickListener(this);
        ef13 = findViewById(R.id.ef13);
        ef13.setOnClickListener(this);
        ef14 = findViewById(R.id.ef14);
        ef14.setOnClickListener(this);
        ef15 = findViewById(R.id.ef15);
        ef15.setOnClickListener(this);
        ef16 = findViewById(R.id.ef16);
        ef16.setOnClickListener(this);
        ef17 = findViewById(R.id.ef17);
        ef17.setOnClickListener(this);
        ef18 = findViewById(R.id.ef18);
        ef18.setOnClickListener(this);
        ef19 = findViewById(R.id.ef19);
        ef19.setOnClickListener(this);
        ef20 = findViewById(R.id.ef20);
        ef20.setOnClickListener(this);
        ef21 = findViewById(R.id.ef21);
        ef21.setOnClickListener(this);
        ef22 = findViewById(R.id.ef22);
        ef22.setOnClickListener(this);
        ExclusiveBlender_Effect.applyEffectNone(ef_original);
        ExclusiveBlender_Effect.applyEffect1(ef1);
        ExclusiveBlender_Effect.applyEffect2(ef2);
        ExclusiveBlender_Effect.applyEffect3(ef3);
        ExclusiveBlender_Effect.applyEffect4(ef4);
        ExclusiveBlender_Effect.applyEffect5(ef5);
        ExclusiveBlender_Effect.applyEffect6(ef6);
        ExclusiveBlender_Effect.applyEffect9(ef9);
        ExclusiveBlender_Effect.applyEffect10(ef10);
        ExclusiveBlender_Effect.applyEffect11(ef11);
        ExclusiveBlender_Effect.applyEffect12(ef12);
        ExclusiveBlender_Effect.applyEffect13(ef13);
        ExclusiveBlender_Effect.applyEffect14(ef14);
        ExclusiveBlender_Effect.applyEffect15(ef15);
        ExclusiveBlender_Effect.applyEffect16(ef16);
        ExclusiveBlender_Effect.applyEffect17(ef17);
        ExclusiveBlender_Effect.applyEffect18(ef18);
        ExclusiveBlender_Effect.applyEffect19(ef19);
        ExclusiveBlender_Effect.applyEffect20(ef20);
        ExclusiveBlender_Effect.applyEffect21(ef21);
        ExclusiveBlender_Effect.applyEffect22(ef22);
    }

    private void bindView() {
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        save = findViewById(R.id.save);
        save.setOnClickListener(this);
        ll_detail = findViewById(R.id.ll_detail);
        ll_effect_list = findViewById(R.id.ll_effect_list);
        llText = findViewById(R.id.ll_text);
        llText.setOnClickListener(this);
        frm_Main = findViewById(R.id.frm_Main);
        llFilter = findViewById(R.id.ll_filter);
        llFilter.setOnClickListener(this);
        llSticker = findViewById(R.id.ll_sticker);
        llSticker.setOnClickListener(this);
        llRotate = findViewById(R.id.ll_Rotate);
        llRotate.setOnClickListener(this);
        llFlip = findViewById(R.id.ll_Flip);
        llFlip.setOnClickListener(this);
        orgImage = findViewById(R.id.org_Img);
        orgImage.setImageBitmap(ExclusiveBlender_ExposureActivity.f4425f);
        fl_Sticker = findViewById(R.id.fl_Sticker);
        Img_light = findViewById(R.id.Img_light);
        seekBrightness =findViewById(R.id.seek_brightness);
        seekBrightness.setOnSeekBarChangeListener(new C10311());
        fl_Sticker.setOnClickListener(new C10333());
        bindEffectIcon();
    }

    private void createDir() {
        ExclusiveBlender_MyClass.createDirIfNotExists(ExclusiveBlender_MyClass.Edit_Folder_name);
    }


    public static Bitmap loadBitmapFromView(View view) {
        if (view.getMeasuredHeight() <= 0) {
            view.measure(-2, -2);
            bmp = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Config.ARGB_8888);
            f4477c = new Canvas(bmp);
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.draw(f4477c);
            return bmp;
        }
        bmp = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
        f4477c = new Canvas(bmp);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(f4477c);
        return bmp;
    }


    private void setArraylistForSticker() {
        stickerList.add(Integer.valueOf(R.drawable.s1));
        stickerList.add(Integer.valueOf(R.drawable.s2));
        stickerList.add(Integer.valueOf(R.drawable.s3));
        stickerList.add(Integer.valueOf(R.drawable.s4));
        stickerList.add(Integer.valueOf(R.drawable.s5));
        stickerList.add(Integer.valueOf(R.drawable.s6));
        stickerList.add(Integer.valueOf(R.drawable.s7));
        stickerList.add(Integer.valueOf(R.drawable.s8));
        stickerList.add(Integer.valueOf(R.drawable.s9));
        stickerList.add(Integer.valueOf(R.drawable.s10));
        stickerList.add(Integer.valueOf(R.drawable.s11));
        stickerList.add(Integer.valueOf(R.drawable.s12));
        stickerList.add(Integer.valueOf(R.drawable.s13));
        stickerList.add(Integer.valueOf(R.drawable.s14));
        stickerList.add(Integer.valueOf(R.drawable.s15));
        stickerList.add(Integer.valueOf(R.drawable.s16));
        stickerList.add(Integer.valueOf(R.drawable.s17));
        stickerList.add(Integer.valueOf(R.drawable.s18));
        stickerList.add(Integer.valueOf(R.drawable.s19));
        stickerList.add(Integer.valueOf(R.drawable.s20));
    }

    private void setBlackAndWhite(ImageView imageView, int i) {
        float f = (float) (i - 255);
        mainMatrix[4] = f;
        mainMatrix[9] = f;
        mainMatrix[14] = f;
        imageView.setColorFilter(new ColorMatrixColorFilter(mainMatrix));
    }

    private void setCurrentEdit(ExclusiveBlender_StickerView exclusiveBlenderStickerView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        mCurrentView = exclusiveBlenderStickerView;
        exclusiveBlenderStickerView.setInEdit(true);
    }

    private void setCurrentEditForText(ExclusiveBlender_TextView customTextView) {
        if (mCurrentTextView != null) {
            mCurrentTextView.setInEdit(false);
        }
        mCurrentTextView = customTextView;
        customTextView.setInEdit(true);
    }


    Bitmap mo14205a(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = width;
        width = -1;
        int i2 = -1;
        int i3 = 0;
        while (i3 < bitmap.getHeight()) {
            int i4 = 0;
            while (i4 < bitmap.getWidth()) {
                int i5;
                if (((bitmap.getPixel(i4, i3) >> 24) & 255) > 0) {
                    i5 = i4 < i ? i4 : i;
                    if (i4 > width) {
                        width = i4;
                    }
                    i = i3 < height ? i3 : height;
                    if (i3 > i2) {
                        height = width;
                        width = i3;
                    } else {
                        height = width;
                        width = i2;
                    }
                } else {
                    i5 = i;
                    i = height;
                    height = width;
                    width = i2;
                }
                i4++;
                i2 = width;
                width = height;
                height = i;
                i = i5;
            }
            i3++;
        }
        return (width < i || i2 < height) ? null : Bitmap.createBitmap(bitmap, i, height, (width - i) + 1, (i2 - height) + 1);
    }
    
    protected void mo14206c() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.activity_text);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        f4478p = (InputMethodManager) getSystemService("input_method");
        f4478p.toggleSoftInput(2, 0);
        final TextView textView = new TextView(this);
        edittext = (EditText) dialog.findViewById(R.id.edittext);
        edittext.requestFocus();
        lyfontlist = dialog.findViewById(R.id.lyfontlist);
        lyfontlist.setVisibility(View.GONE);
        gvfontlist =  dialog.findViewById(R.id.gvfontlist);
        gvfontlist.setAdapter(new ExclusiveBlender_FontAdapter(this, f4482t));
        gvfontlist.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                type = Typeface.createFromAsset(getAssets(), f4482t[i]);
                edittext.setTypeface(type);
                textView.setTypeface(type);
            }
        });
        lycolorlist = dialog.findViewById(R.id.lycolorlist);
        lycolorlist.setVisibility(View.GONE);
        gvcolorlist =  dialog.findViewById(R.id.gvcolorlist);
        ArrayList HSVColors = HSVColors();
        final ArrayList arrayList = HSVColors;
        gvcolorlist.setAdapter(new ArrayAdapter<Integer>(getApplicationContext(), 17367043, HSVColors) {
            public View getView(int i, View view, ViewGroup viewGroup) {
                TextView textView = (TextView) super.getView(i, view, viewGroup);
                textView.setBackgroundColor(((Integer) arrayList.get(i)).intValue());
                textView.setText("");
                textView.setLayoutParams(new AbsListView.LayoutParams(-1, -1));
                AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) textView.getLayoutParams();
                layoutParams.width = ExclusiveBlender_EditingActivity.columnWidth;
                layoutParams.height = ExclusiveBlender_EditingActivity.columnWidth;
                textView.setLayoutParams(layoutParams);
                textView.requestLayout();
                return textView;
            }
        });
        gvcolorlist.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                mPickedColor = ((Integer) adapterView.getItemAtPosition(i)).intValue();
                edittext.setTextColor(mPickedColor);
                textView.setTextColor(mPickedColor);
            }
        });
        iv_keyboard = dialog.findViewById(R.id.iv_keyboard);
        iv_keyboard.setOnClickListener(new C10388());
        iv_fontstyle = dialog.findViewById(R.id.iv_fontstyle);
        iv_fontstyle.setOnClickListener(new C10399());
        iv_color = dialog.findViewById(R.id.iv_color);
        iv_color.setOnClickListener(new C102410());
        iv_gravity = dialog.findViewById(R.id.iv_gravity);
        iv_gravity.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (f4485w == 0) {
                    f4485w = 1;
                    iv_gravity.setImageDrawable(getResources().getDrawable(R.drawable.alignright));
                    edittext.setGravity(5);
                    textView.setGravity(5);
                } else if (f4485w == 1) {
                    iv_gravity.setImageDrawable(getResources().getDrawable(R.drawable.alignleft));
                    edittext.setGravity(3);
                    textView.setGravity(3);
                    f4485w = 2;
                } else if (f4485w == 2) {
                    f4485w = 0;
                    iv_gravity.setImageDrawable(getResources().getDrawable(R.drawable.aligncenter));
                    edittext.setGravity(17);
                    textView.setGravity(17);
                }
            }
        });
        iv_done = dialog.findViewById(R.id.iv_done);
        final TextView textView2 =  dialog.findViewById(R.id.txtEnteredText);
        textView2.setDrawingCacheEnabled(true);
        iv_done.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                f4478p.hideSoftInputFromWindow(view.getWindowToken(), 0);
                String obj = edittext.getText().toString();
                if (obj.isEmpty()) {
                    Toast.makeText(ExclusiveBlender_EditingActivity.this, "text empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                textView2.setText(obj);
                textView2.setTypeface(type);
                textView2.setTextColor(mPickedColor);
                textView2.setGravity(17);
                ImageView imageView = new ImageView(ExclusiveBlender_EditingActivity.this);
                textView2.buildDrawingCache();
                imageView.setImageBitmap(textView2.getDrawingCache());
                ExclusiveBlender_EditingActivity.textBitmap = ExclusiveBlender_EditingActivity.loadBitmapFromView(imageView);
                ExclusiveBlender_EditingActivity.textBitmap = mo14205a(ExclusiveBlender_EditingActivity.textBitmap);
                textView2.setDrawingCacheEnabled(false);
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(edittext.getWindowToken(), 0);
                final ExclusiveBlender_TextView customTextView = new ExclusiveBlender_TextView(ExclusiveBlender_EditingActivity.this);
                customTextView.setBitmap(ExclusiveBlender_EditingActivity.textBitmap);
                fl_Sticker.addView(customTextView, new FrameLayout.LayoutParams(-1, -1, 17));
                mStickers.add(customTextView);
                customTextView.setInEdit(true);
                setCurrentEditForText(customTextView);
                customTextView.setOperationListener(new ExclusiveBlender_TextView.OperationListener() {
                    public void onDeleteClick() {
                        mStickers.remove(customTextView);
                        fl_Sticker.removeView(customTextView);
                    }

                    public void onEdit(ExclusiveBlender_TextView customTextView) {
                        ExclusiveBlender_EditingActivity.mCurrentTextView.setInEdit(false);
                        ExclusiveBlender_EditingActivity.mCurrentTextView = customTextView;
                        ExclusiveBlender_EditingActivity.mCurrentTextView.setInEdit(true);
                    }

                    public void onTop(ExclusiveBlender_TextView customTextView) {
                        int indexOf = mStickers.indexOf(customTextView);
                        if (indexOf != mStickers.size() - 1) {
                            mStickers.add(mStickers.size(), (ExclusiveBlender_TextView) mStickers.remove(indexOf));
                        }
                    }
                });
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -1;
        window.setAttributes(layoutParams);
    }

    public void onClick(View view) {
        isLight = false;
        switch (view.getId()) {
            case R.id.back:
                finish();
                return;
            case R.id.save:
                ll_effect_list.setVisibility(View.GONE);
                ll_detail.setVisibility(View.GONE);
                seekBrightness.setVisibility(View.GONE);
                ll_effect_list.setVisibility(View.GONE);
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
                if (mCurrentTextView != null) {
                    mCurrentTextView.setInEdit(false);
                }

                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                } else if (mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                } else {
                    new saveImage().execute();
                }

                return;
            case R.id.frm_Main:
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
                if (mCurrentTextView != null) {
                    mCurrentTextView.setInEdit(false);
                    return;
                }
                return;
            case R.id.ef_original:
                ExclusiveBlender_Effect.applyEffectNone(orgImage);
                return;
            case R.id.ef1:
                ExclusiveBlender_Effect.applyEffect1(orgImage);
                return;
            case R.id.ef2:
                ExclusiveBlender_Effect.applyEffect2(orgImage);
                return;
            case R.id.ef3 :
                ExclusiveBlender_Effect.applyEffect3(orgImage);
                return;
            case R.id.ef4 :
                ExclusiveBlender_Effect.applyEffect4(orgImage);
                return;
            case R.id.ef5 :
                ExclusiveBlender_Effect.applyEffect5(orgImage);
                return;
            case R.id.ef6 :
                ExclusiveBlender_Effect.applyEffect6(orgImage);
                return;
            case R.id.ef9 :
                ExclusiveBlender_Effect.applyEffect9(orgImage);
                return;
            case R.id.ef10 :
                ExclusiveBlender_Effect.applyEffect10(orgImage);
                return;
            case R.id.ef11 :
                ExclusiveBlender_Effect.applyEffect11(orgImage);
                return;
            case R.id.ef12 :
                ExclusiveBlender_Effect.applyEffect12(orgImage);
                return;
            case R.id.ef13 :
                ExclusiveBlender_Effect.applyEffect13(orgImage);
                return;
            case R.id.ef14 :
                ExclusiveBlender_Effect.applyEffect14(orgImage);
                return;
            case R.id.ef15 :
                ExclusiveBlender_Effect.applyEffect15(orgImage);
                return;
            case R.id.ef16 :
                ExclusiveBlender_Effect.applyEffect16(orgImage);
                return;
            case R.id.ef17 :
                ExclusiveBlender_Effect.applyEffect17(orgImage);
                return;
            case R.id.ef18 :
                ExclusiveBlender_Effect.applyEffect18(orgImage);
                return;
            case R.id.ef19 :
                ExclusiveBlender_Effect.applyEffect19(orgImage);
                return;
            case R.id.ef20 :
                ExclusiveBlender_Effect.applyEffect20(orgImage);
                return;
            case R.id.ef21 :
                ExclusiveBlender_Effect.applyEffect21(orgImage);
                return;
            case R.id.ef22 :
                ExclusiveBlender_Effect.applyEffect22(orgImage);
                return;
            case R.id.ll_filter :
                seekBrightness.setVisibility(View.GONE);
                ll_detail.setVisibility(View.VISIBLE);
                ll_effect_list.setVisibility(View.VISIBLE);
                if (isBrightness) {
                    ll_effect_list.setVisibility(View.VISIBLE);
                    ll_detail.setVisibility(View.VISIBLE);
                    seekBrightness.setVisibility(View.GONE);
                    isBrightness = false;
                    return;
                }
                ll_effect_list.setVisibility(View.GONE);
                ll_detail.setVisibility(View.GONE);
                seekBrightness.setVisibility(View.GONE);
                isBrightness = true;
                return;
            case R.id.ll_text:
                ll_effect_list.setVisibility(View.GONE);
                mo14206c();
                return;
            case R.id.ll_sticker :
                ll_effect_list.setVisibility(View.GONE);
                showStickerDialog();
                return;
            case R.id.ll_Rotate:
                if (rotate == 1) {
                    orgImage.setRotation(90.0f);
                    rotate = 2;
                    return;
                } else if (rotate == 2) {
                    orgImage.setRotation(180.0f);
                    rotate = 3;
                    return;
                } else if (rotate == 3) {
                    orgImage.setRotation(270.0f);
                    rotate = 4;
                    return;
                } else if (rotate == 4) {
                    orgImage.setRotation(360.0f);
                    rotate = 1;
                    return;
                } else {
                    return;
                }
            case R.id.ll_Flip:
                ll_effect_list.setVisibility(View.GONE);
                if (flagForFlip) {
                    orgImage.setRotationY(180.0f);
                    flagForFlip = false;
                    return;
                }
                orgImage.setRotationY(360.0f);
                flagForFlip = true;
                return;
            default:
                return;
        }
    }


    protected void onResume() {
        super.onResume();
    }

    public void showStickerDialog() {
        final Dialog dialog = new Dialog(this, 16973839);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.sticker_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(true);
        ((ImageView) dialog.findViewById(R.id.back_dialog)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        exclusiveBlenderStickerAdapter = new ExclusiveBlender_StickerAdapter(getApplicationContext(), stickerList);
        GridView gridView =  dialog.findViewById(R.id.gridStickerList);
        gridView.setAdapter(exclusiveBlenderStickerAdapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                addStickerView(((Integer) stickerList.get(i)).intValue());
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    class saveImage extends AsyncTask {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(ExclusiveBlender_EditingActivity.this, R.style.AppCompatAlertDialogStyle);
            pd.setMessage("Saving Image!!");
            pd.show();
        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
            saveEditorImage();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (pd != null) {
                pd.dismiss();
            }
            Intent intent = new Intent(ExclusiveBlender_EditingActivity.this, ExclusiveBlender_ShareImageActivity.class);
            intent.putExtra("imgPath", file.getAbsolutePath());
            intent.putExtra("back", "1");
            startActivity(intent);
            finish();
        }
    }

    protected boolean saveEditorImage() {
        // TODO Auto-generated method stub
        path = getOutPutPath();
        file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {

            selectedImg = getFrameBitmap();

            FileOutputStream fos = null;
            mImageSavedUri = Uri.parse("file://" + file.getPath());
            fos = new FileOutputStream(file);
            selectedImg = ExclusiveBlender_Utils.TrimBitmap(selectedImg);
            selectedImg.compress(Bitmap.CompressFormat.PNG, 80, fos);
            fos.flush();
            fos.close();

            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, new String[]{"image/png"},
                    null);

            refreshGallery(file);

            Log.e("Vimal", file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String getOutPutPath() {
        // TODO Auto-generated method stub

        File folder = new File(Environment.getExternalStorageDirectory() + "/" + applicationName);

        if (!folder.exists()) {
            folder.mkdirs();
            return Environment.getExternalStorageDirectory() + "/"
                    + applicationName + "/" + "ExclusivePhotoBlender_"
                    + System.currentTimeMillis() + ".png";
        } else {
            return Environment.getExternalStorageDirectory() + "/"
                    + applicationName + "/" + "ExclusivePhotoBlender_"
                    + System.currentTimeMillis() + ".png";
        }
    }

    public Bitmap getFrameBitmap() {
        Bitmap bm = null;
        frm_Main.postInvalidate();
        frm_Main.setDrawingCacheEnabled(true);
        frm_Main.buildDrawingCache();
        bm = Bitmap.createBitmap(frm_Main.getDrawingCache());
        frm_Main.destroyDrawingCache();
        return bm;
    }

    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

}
