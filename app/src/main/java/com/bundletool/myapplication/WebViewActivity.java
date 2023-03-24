package com.bundletool.myapplication;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bundletool.myapplication.base.BaseWebView;
import com.bundletool.myapplication.base.WebViewCallBack;
import com.bundletool.myapplication.load.LoadService;
import com.bundletool.myapplication.load.LoadServiceImpl;
import com.bundletool.myapplication.load.callback.EmptyCallBack;
import com.bundletool.myapplication.load.callback.ErrorCallBack;
import com.bundletool.myapplication.load.callback.BaseLoadCallBack;
import com.bundletool.myapplication.load.callback.LoadingCallback;
import com.bundletool.myapplication.load.callback.SuccessCallback;

public class WebViewActivity extends Activity{

    private BaseWebView webView;

    private String path = "https://gpassport.37games.com/login/dirLogin?country=CN&gaid=675eeeba-c14b-4768-9a2a-1916a27e9c26&sign=e153b1ae5e38465fee0fa4cd17c72a85&language=zh-CN&thirdPlatForm=mac&installTime=1676546640714&jgPid=1&battery=88&serverId=999&mac=&deepLinkURL=&isFirst=0&uid=1104903614&phoneModel=Pixel+4a&devicePlate=android&osVersion=13&userMode=2&publishPlatForm=&zone=zh-CN&loginName=MVFN%3A1104903614&packageName=com.global.ztmslg.amazon&Isdblink=0&channelId=&ueAndroidId=5968c901051b201c&apps=1676546643695-2713191903467117252&sdkVersionName=3.0.6&advertiser=global&gameId=191&customUserId=ffb02ca2-d728-4683-8b9a-28e6918014c1&isTrackEnabled=1&netType=WIFI&forward=https%3A%2F%2Fgabres.37games.com%2Fplatform%2Fsupport.html%3Fcountry%3DCN%26loginAccount%3DMVFN%253A1104903614%26language%3Dzh-CN%26jgPid%3D1%26serverId%3D999%26deepLinkURL%3D%26isFirst%3D0%26phoneModel%3DPixel%2B4a%26userMode%3D2%26zone%3Dzh-CN%26Isdblink%3D0%26netType%3DWIFI%26timeZone%3DGMT%252B08%253A00%26ptCode%3Dglobal%26loginUID%3D1104903614%26roleName%3D%25E6%2584%259B%25E7%2588%25BE%25C2%25B7%25E9%25AE%2591%25E7%2588%25BE%26sdkVersion%3D354%26androidid%3Daed54ec7eb7f8d6840f6333b97aef1eb%26loginSign%3D393209e3e88e50f0fd73d99970aa8af18b5aa24902b88ec1fcf0d454930028695bb793971149b9c6d38705eca0384f84831433af0abf6fc30906c9dd95679413b744880554f57fb740cf372449e6f21f53a6369909cd77acf31744a7b6cd700a%26gaid%3D675eeeba-c14b-4768-9a2a-1916a27e9c26%26thirdPlatForm%3Dmac%26installTime%3D1676546640714%26battery%3D88%26mac%3D%26devicePlate%3Dandroid%26osVersion%3D13%26publishPlatForm%3D%26packageName%3Dcom.global.ztmslg.amazon%26roleLevel%3D260%26loginTimestamp%3D1676967368%26skinBrand%3D37games%26channelId%3D%26ueAndroidId%3D5968c901051b201c%26apps%3D1676546643695-2713191903467117252%26sdkToken%3Dglobalztmslglink%26sdkVersionName%3D3.0.6%26advertiser%3Dglobal%26gameId%3D191%26customUserId%3Dffb02ca2-d728-4683-8b9a-28e6918014c1%26isTrackEnabled%3D1%26roleId%3D999000026%26packageVersion%3D1%26userName%3D1104903614%26userId%3D1104903614%26timeStamp%3D1676967374970%26gpid%3D675eeeba-c14b-4768-9a2a-1916a27e9c26%26isVpnOn%3D0%26imei%3D%26ratio%3D1080x2340%26appLanguage%3Dzh-CN%23%2Fmylist&loginToken=393209e3e88e50f0fd73d99970aa8af18b5aa24902b88ec1fcf0d454930028695bb793971149b9c6d38705eca0384f84831433af0abf6fc30906c9dd95679413b744880554f57fb740cf372449e6f21f53a6369909cd77acf31744a7b6cd700a&timeZone=GMT%2B08%3A00&packageVersion=1&ptCode=global&timeStamp=1676967374977&gpid=675eeeba-c14b-4768-9a2a-1916a27e9c26&isVpnOn=0&imei=&sdkVersion=354&gameCode=ztmslg&androidid=aed54ec7eb7f8d6840f6333b97aef1eb&ratio=1080x2340&appLanguage=zh-CN";

    private OrientationEventListener listener;
    private ConstraintLayout clContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openFullScreenModel(this);
        setContentView(R.layout.activity_web);
//        setFullScreen(true);

        path = "www.baidu.com";
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
        clContent = findViewById(R.id.cl_content);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                Log.e("webview", "onReceivedError");
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
//                Log.e("webview", "onReceivedSslError");
//            }
//        });
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        if (path.contains("baidu")){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        webView.loadUrl(path);

        // 1. 初始化builder，加载到LoadServiceImpl里面
//        LoadServiceImpl.Builder.Companion.getBeginBuilder()
//                .addCallBack(new EmptyCallBack())
//                .addCallBack(new ErrorCallBack())
//                .addCallBack(new LoadingCallback())
//                .setDefaultCallback(LoadingCallback.class)
//                .commit();
        // 2. 初始化LoadService
        LoadService loadService = LoadServiceImpl.Companion.getInstance().register(clContent);
        webView.registerWebViewCallBack(new WebViewCallBack() {
            @Override
            public void pageStarted(@Nullable String url) {
                Log.e("loadsir", "pageStarted");
                loadService.showCallback(LoadingCallback.class);
            }

            @Override
            public void pageFinished(@Nullable String url) {
                Log.e("loadsir", "pageFinished");

                //成功
                loadService.showSuccess();
//                loadService.showCallback(ErrorCallBack.class);
            }

            @Override
            public void onError() {
                Log.e("loadsir", "onError");
                //设置失败页面
                loadService.showCallback(ErrorCallBack.class);
            }
        });
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

    public void openFullScreenModel(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            activity.getWindow().setAttributes(lp);
            View decorView = activity.getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            systemUiVisibility |= flags;
            activity.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        }
    }

    private void setFullScreen(boolean fullScreen)
    {
        getWindow().setLayout(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
        if (fullScreen)
        {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attr);
        }
        else
        {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        {
            if (fullScreen)
            {
                View decorView = getWindow().getDecorView();
                int systemUiVisibility = decorView.getSystemUiVisibility();
                systemUiVisibility |= View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
            }
            else
            {
                View decorView = getWindow().getDecorView();
                int systemUiVisibility = decorView.getSystemUiVisibility();
                systemUiVisibility &= (~View.SYSTEM_UI_FLAG_FULLSCREEN);
                systemUiVisibility &= (~View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                systemUiVisibility &= (~View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

//    class IWebViewCallBack implements WebViewCallBack {
//        private IWebViewCallBack mCallBack;
//        IWebViewCallBack(IWebViewCallBack callBack){
//            mCallBack = callBack;
//        }
//
//        @Override
//        public void pageStarted(@Nullable String url) {
//
//        }
//
//        @Override
//        public void pageFinished(@Nullable String url) {
//
//        }
//
//        @Override
//        public void onError() {
//
//        }
//    }



}
