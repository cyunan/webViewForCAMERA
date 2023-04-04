package com.bundletool.myapplication.load.callback

import android.content.Context
import android.view.View
import com.bundletool.myapplication.R

class LoadingCallback : BaseLoadCallBack(){
    override fun onCreateView(): Int {
        return R.layout.webview_loading
    }
    override fun getSuccessVisible(): Boolean {
        return super.getSuccessVisible()
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}