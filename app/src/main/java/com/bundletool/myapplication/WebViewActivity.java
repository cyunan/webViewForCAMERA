package com.bundletool.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    private String path = "https://www.baidu.com/";
    private OrientationEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
//        listener = new OrientationEventListener(this) {
//            @Override
//            public void onOrientationChanged(int orientation) {
//                Log.i("lala","onOrientationChanged===>" + orientation);
//                boolean isPortrait = PolyvScreenUtils.isPortrait(WebViewActivity.this);
//                if ((orientation > -1 && orientation <= 10) || orientation >= 350 || (orientation <= 190 && orientation >= 170)) {
//                    if (!isPortrait){
//                        PolyvScreenUtils.setPortrait(WebViewActivity.this);
//                    }
//
//                } else if ((orientation <= 100 && orientation >= 80) || (orientation <= 280 && orientation >= 260)) {
//                    if (isPortrait){
//                        PolyvScreenUtils.setLandscape(WebViewActivity.this);
//                    }
//                }
//            }
//        };
//        listener.enable();
        listener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
        };
        listener.enable();
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e("webview", "onReceivedError");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Log.e("webview", "onReceivedSslError");
            }
        });
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        if (path.contains("baidu")){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        webView.loadUrl(path);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listener != null){
            listener.disable();
        }
        if (webView != null) {
            webView.stopLoading();
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }






}
