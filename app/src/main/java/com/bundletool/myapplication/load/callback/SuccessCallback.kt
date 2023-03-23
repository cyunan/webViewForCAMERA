package com.bundletool.myapplication.load.callback

import android.content.Context
import android.view.View

class SuccessCallback(view: View, context: Context, onReloadListener: OnReloadListener?)
    : BaseLoadCallBack(view, context, onReloadListener){
    override fun onCreateView(): Int {
        return 0
    }

    fun hide(){
        obtainRootView()?.visibility = View.GONE
    }

    fun show() {
        obtainRootView()?.visibility = View.VISIBLE
    }

    /**
     *  根据回调决定是否展示根布局
     */
    fun showWithCallback(successVisible: Boolean) {
        obtainRootView()?.visibility = if (successVisible) View.VISIBLE else View.INVISIBLE
    }
}