package com.bundletool.myapplication.base

import android.graphics.Bitmap
import android.net.http.SslError
import android.util.Log
import android.webkit.*

class BaseWebViewClient(private val callBack: WebViewCallBack?) : WebViewClient() {
    private var mIsError = false
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.e("loadsir1", "onPageStarted")
        Log.e("loadsir1", "progress:${view?.progress}")
        if (view?.progress != 100){
            callBack?.pageStarted(url)
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.e("loadsir1", "onPageFinished")
        Log.e("loadsir1", "progress:${view?.progress}")
        if (view?.progress == 100 && !mIsError){
            callBack?.pageFinished(url)
        }else{
            callBack?.onError()
        }
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        Log.e("loadsir1", "onReceivedError")
        Log.e("loadsir1", "progress:${view?.progress}")
        mIsError = true
    }


}