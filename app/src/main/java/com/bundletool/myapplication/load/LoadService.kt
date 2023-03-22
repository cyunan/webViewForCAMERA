package com.bundletool.myapplication.load

import javax.security.auth.callback.Callback

class LoadService {
    private var builder: Builder? = null

    companion object{
        val instance: LoadService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){LoadService()}
    }
    class Builder{
        companion object{
            val beginBuilder by lazy { Builder() }
        }
        private val callbacks = mutableListOf<LoadCallBack>()

        fun addCallBack(callback: LoadCallBack){
            callbacks.add(callback)
        }

        fun commit(){
            LoadService.instance.builder = this
        }
    }


}