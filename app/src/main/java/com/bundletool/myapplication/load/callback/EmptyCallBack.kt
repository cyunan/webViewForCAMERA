package com.bundletool.myapplication.load.callback

import com.bundletool.myapplication.R


class EmptyCallBack : BaseLoadCallBack(){
    override fun onCreateView(): Int {
        return R.layout.webview_empty
    }
}