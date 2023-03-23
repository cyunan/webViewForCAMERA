package com.bundletool.myapplication.load.callback

import com.bundletool.myapplication.R


class ErrorCallBack : BaseLoadCallBack(){
    override fun onCreateView(): Int {
        return R.layout.webview_empty
    }
}