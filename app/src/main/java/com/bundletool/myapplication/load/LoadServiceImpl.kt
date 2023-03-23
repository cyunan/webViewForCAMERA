package com.bundletool.myapplication.load

import com.bundletool.myapplication.load.callback.BaseLoadCallBack

class LoadServiceImpl {
//    private lateinit var builder: Builder
    private var builder: Builder? = null

    companion object{
        val instance: LoadServiceImpl by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){LoadServiceImpl()}
    }

    fun register(target: Any?) = register(target, null)

    /**
     * target view or activity
     */
    fun register(target: Any?, onReloadListener: BaseLoadCallBack.OnReloadListener?): LoadService{
        val targetContext = LoadUtil.getTargetContent(target)
        return LoadService(targetContext, onReloadListener, builder)

    }
    class Builder{
        companion object{
            val beginBuilder by lazy { Builder() }
        }

        // TODO: 完善数据可见性
        val callbacks = mutableListOf<BaseLoadCallBack>()
        var defaultCallback: Class<out BaseLoadCallBack>? = null
            private set


        fun addCallBack(callback: BaseLoadCallBack): Builder{
            callbacks.add(callback)
            return this
        }

        fun setDefaultCallback(defaultCallback: Class<out BaseLoadCallBack>): Builder{
            this.defaultCallback = defaultCallback
            return this
        }

        fun commit(){
            LoadServiceImpl.instance.builder = this
        }
    }


}