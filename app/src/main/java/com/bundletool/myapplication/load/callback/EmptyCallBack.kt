package com.bundletool.myapplication.load.callback

import com.bundletool.myapplication.R
import com.bundletool.myapplication.load.LoadCallBack


class EmptyCallBack : LoadCallBack(){
    override fun onCreateView(): Int {
        return R.layout.webview_empty
    }
}