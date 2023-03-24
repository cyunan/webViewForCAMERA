package com.bundletool.myapplication

import android.app.Application
import com.bundletool.myapplication.load.LoadServiceImpl.Builder.Companion.beginBuilder
import com.bundletool.myapplication.load.callback.EmptyCallBack
import com.bundletool.myapplication.load.callback.ErrorCallBack
import com.bundletool.myapplication.load.callback.LoadingCallback

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 1. 初始化builder，加载到LoadServiceImpl里面
        beginBuilder
            .addCallBack(EmptyCallBack())
            .addCallBack(ErrorCallBack())
            .addCallBack(LoadingCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }
}