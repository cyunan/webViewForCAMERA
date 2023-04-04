package com.bundletool.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import com.bundletool.myapplication.base.BaseWebView;
import com.bundletool.myapplication.base.WebViewCallBack;
import com.bundletool.myapplication.load.LoadService;
import com.bundletool.myapplication.load.LoadServiceImpl;
import com.bundletool.myapplication.load.callback.ErrorCallBack;
import com.bundletool.myapplication.load.callback.LoadingCallback;
import com.bundletool.myapplication.push.NotificationReceiver;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

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

        path = "https://juejin.cn/?utm_source=gold_browser_extension";
//        path = "http://baidu.com";
//        path = "https://gabres.37games.com/platform/support.html?Isdblink=0&advertiser=global&androidid=aed54ec7eb7f8d6840f6333b97aef1eb&appLanguage=zh-CN&apps=1679894272633-6521975428286192471&battery=100&channelId=&country=CN&customUserId=8a4da0fa-223b-43fb-a5e5-00b12b3677cb&deepLinkURL=&devicePlate=android&gaid=675eeeba-c14b-4768-9a2a-1916a27e9c26&gameCode=ztmslg&gameId=191&gpid=675eeeba-c14b-4768-9a2a-1916a27e9c26&imei=&installTime=1679894268526&isFirst=0&isTrackEnabled=1&isVpnOn=0&jgPid=1&language=zh-CN&loginAccount=MVFN%3A1104903614&loginName=MVFN%3A1104903614&loginSign=02ae981ca62f6e82c90cd5c89d1a919438686f0ed2a3035daac77d7a29694e9c1bd7ab4f3dc2a0b4396fb1a36ffefc0d718072a661a42de1221424efdbd1d9daea89ebce16fa1204207f135748e45c99fa6e0c7159b1df0e5bdec9ce5fe64317&loginTimestamp=1680061101&loginToken=02ae981ca62f6e82c90cd5c89d1a919438686f0ed2a3035daac77d7a29694e9c1bd7ab4f3dc2a0b4396fb1a36ffefc0d718072a661a42de1221424efdbd1d9daea89ebce16fa1204207f135748e45c99fa6e0c7159b1df0e5bdec9ce5fe64317&loginUID=1104903614&mac=&netType=WIFI&osVersion=13&packageName=com.global.ztmslg&packageVersion=1&phoneModel=Pixel+4a&ptCode=global&publishPlatForm=&ratio=1080x2340&roleId=&roleLevel=&roleName=&sdkToken=globalztmslglink&sdkVersion=358&sdkVersionName=3.0.10&serverId=&sign=4dcebc7d7562a94ed84bf8eb085af229&skinBrand=37games&thirdPlatForm=mac&timeStamp=1680061106007&timeZone=GMT%2B08%3A00&ueAndroidId=5968c901051b201c&uid=1104903614&userId=1104903614&userMode=2&userName=1104903614&zone=zh-CN#/mylist";
//        listener = new OrientationEventListener(this) {
//            @Override
//            public void onOrientationChanged(int orientation) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//            }
//        };
//        listener.enable();
        webView = findViewById(R.id.webView);
        clContent = findViewById(R.id.cl_content);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
//        if (path.contains("baidu")){
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//        }
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
                webView.removeBlankMonitorRunnable();
                webView.postBlankMonitorRunnable();
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
        }, new BaseWebView.BlankMonitorCallback1() {
            @Override
            public void onBlank() {
                Log.e("WebViewActivity", "发生白屏");
                new AlertDialog.Builder(WebViewActivity.this)
                        .setTitle("提示")
                        .setMessage("检测到页面发生异常，是否重新加载？")
                        .setPositiveButton("重新加载", (dialog, which) -> {
                            dialog.dismiss();
                            webView.reload();
                        })
                        .create()
                        .show();

            }
        });


//        initNotification();
    }

    private void initNotification() {
        int requestCode = (int) System.currentTimeMillis();
        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getBroadcast(this,
                    requestCode, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        }else {
            pendingIntent = PendingIntent.getBroadcast(this,
                    requestCode, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 如果该channel已经存在，则可以不用再次创建
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            //是否绕过请勿打扰模式
            channel.canBypassDnd();
            //闪光灯
            channel.enableLights(true);
            //锁屏显示通知
            channel.setLockscreenVisibility(1);
            //闪关灯的灯光颜色
            channel.setLightColor(Color.RED);
            //桌面launcher的消息角标
            channel.canShowBadge();
            //是否允许震动
            channel.enableVibration(true);
            //获取系统通知响铃声音的配置
            channel.getAudioAttributes();
            //获取通知取到组
            channel.getGroup();
            //设置可绕过  请勿打扰模式
            channel.setBypassDnd(true);
            //设置震动模式
            channel.setVibrationPattern(new long[]{100, 100, 200});
            //是否会有灯光
            channel.shouldShowLights();
            // 如果该channel已经存在，则可以不用再次创建
            manager.createNotificationChannel(channel);
        }
        // 注意：此处channel_id必须是一个已存在的channel id；否则无法显示通知
        Notification notification = new NotificationCompat.Builder(this, "channel_id")
                .setContentIntent(pendingIntent)
                .setContentTitle("Title").setContentText("This is content").setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round)).build();
        manager.notify(requestCode, notification);
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



}
