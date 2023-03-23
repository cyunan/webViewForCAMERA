package com.bundletool.myapplication.base

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class BaseWebView : WebView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    fun init() {

    }

    fun registerWebViewCallBack(webViewCallBack: WebViewCallBack?) {
        webViewClient = BaseWebViewClient(webViewCallBack)
    }


    companion object {
        const val TAG = "BaseWebView"
    }
}