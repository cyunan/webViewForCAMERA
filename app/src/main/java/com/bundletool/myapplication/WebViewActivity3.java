package com.bundletool.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class WebViewActivity3 extends AppCompatActivity {

    private WebView mWebView = null;
    private ValueCallback<Uri[]> mFilePathCallback = null;
    private int REQUEST_CODE_LOLIPOP = 1;  // 5.0以上版本
    private String mCameraPhotoPath = "";  // 拍照的图片路径
    public static final int REQUEST_CODE = 5;

    private static final String[] PERMISSION = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.RECORD_AUDIO,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview2);
        checkPermission(this);
        mWebView = (WebView) findViewById(R.id.webView);
        showWebView();
        int a = 1;
        int b = a;
        ine c = b;
    }

    private void showWebView() {
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //支持插件
//        webSettings.setPluginsEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setMediaPlaybackRequiresUserGesture(false);


        setWebViewClient();
        setWebChromeClient();
//        mWebView.loadUrl("https://webrtc.github.io/samples/src/content/getusermedia/gum/");
        mWebView.loadUrl("https://qixinweb.37.com/test1.html");
//        showCustomWebChromeClient();

    }

    public static boolean checkPermission(Activity activity){
        if(isPermissionGranted(activity)) {
            return true;
        } else {
            //如果没有设置过权限许可，则弹出系统的授权窗口
            ActivityCompat.requestPermissions(activity,PERMISSION,REQUEST_CODE);
            return false;
        }
    }

    public static boolean isPermissionGranted(Activity activity){
        /***
         * checkPermission返回两个值
         * 有权限: PackageManager.PERMISSION_GRANTED
         * 无权限: PackageManager.PERMISSION_DENIED
         */
        for (String s : PERMISSION) {
            int checkPermission = ContextCompat.checkSelfPermission(activity, s);

            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void setWebViewClient() {
//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();
//            }
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//                mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//        });
        mWebView.setWebViewClient(new WebViewClient());
    }

    private void setWebChromeClient() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

//        mWebView.setWebChromeClient(new PaxWebChromeClient(this){
//
//        });
    }
}