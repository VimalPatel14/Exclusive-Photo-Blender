package com.photovideoeditormaker.exclusivephotoblender.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.photovideoeditormaker.exclusivephotoblender.R;
import com.photovideoeditormaker.exclusivephotoblender.util.ExclusiveBlender_MyClass;

public class ExclusiveBlender_WebActivity extends AppCompatActivity {
    private WebView webPrivacyPolicy;
    private com.facebook.ads.AdView adView;

    class C10571 extends WebViewClient {
        C10571() {
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            Toast.makeText(ExclusiveBlender_WebActivity.this, str, 0).show();
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str.startsWith("http:") || str.startsWith("https:")) {
                return false;
            }
            ExclusiveBlender_WebActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            return true;
        }
    }

    public void onBackPressed() {
        finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_web);
        this.webPrivacyPolicy =  findViewById(R.id.wvPrivacyPolicy);
        WebSettings settings = this.webPrivacyPolicy.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        this.webPrivacyPolicy.setWebViewClient(new C10571());
        this.webPrivacyPolicy.loadUrl(ExclusiveBlender_MyClass.privacy_link);

        showfbbanner();
    }

    private void showfbbanner() {
        adView = new com.facebook.ads.AdView(this, getString(R.string.banner_ad_fb), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
    }
}
