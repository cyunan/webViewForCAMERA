package com.bundletool.myapplication.load.view

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.bundletool.myapplication.load.LoadUtil
import com.bundletool.myapplication.load.callback.BaseLoadCallBack
import com.bundletool.myapplication.load.callback.SuccessCallback

class LoadLayout : FrameLayout{
//    lateinit var context: Context
//    private var context: Context? = null
    // 回调列表
    private var callbacks: MutableMap<Class<out BaseLoadCallBack>, BaseLoadCallBack> = mutableMapOf()

    private var onReloadListener: BaseLoadCallBack.OnReloadListener? = null
    // 上一个回调
    private var preCallback: Class<out BaseLoadCallBack>? = null
    // 当前回调
    private var curCallback: Class<out BaseLoadCallBack>? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, onReloadListener: BaseLoadCallBack.OnReloadListener?): this(context){
        this.onReloadListener = onReloadListener
    }

    /**
     * 设置成功页面
     */
    fun setupSuccessLayout(callBack: BaseLoadCallBack){
        addCallback(callBack)
        val successView: View? = callBack.getRootView()
        successView?.visibility = GONE
        addView(successView)
        curCallback = SuccessCallback::class.java
    }

    /**
     * 设置回调
     */
    fun setupCallback(callBack: BaseLoadCallBack) {
        val cloneCallback = callBack.copy()
        cloneCallback?.let {
            it.setCallback(null, context, onReloadListener)
            addCallback(it)
        }
    }

    /**
     * 添加回调
     */
    private fun addCallback(callBack: BaseLoadCallBack){
        if (!callbacks.containsKey(callBack.javaClass)) {
            callbacks[callBack.javaClass] = callBack
        }
    }

    /**
     * 展示回调的页面（必须在主线程）
     */
    fun showCallback(callback: Class<out BaseLoadCallBack>){
        checkCallbackExist(callback)
        if (LoadUtil.isMainThread()) {
            showCallbackView(callback)
        } else {
            postToMainThread(callback)
        }
    }

    private fun postToMainThread(status: Class<out BaseLoadCallBack>) {
        post { showCallbackView(status) }
    }

    /**
     * 展示页面具体逻辑
     */
    private fun showCallbackView(mCallback: Class<out BaseLoadCallBack?>) {
        //1. 将preCallback对应的视图解绑
        preCallback?.let {
            if (preCallback == mCallback) {
                //前一个callback不为null，且前一个callback与传入的status callback相等，则退出显示callback逻辑
                return
            }
            callbacks[preCallback]?.onDetach()
        }

        //2. 删除loadLayout 容器中的第一个callback中的rootView
        if (childCount > 1) {
            removeViewAt(CALLBACK_CUSTOM_INDEX)
        }

        //3. 展示callback视图
        for (key in callbacks.keys) {
            if (key == mCallback) {
                val successCallback: SuccessCallback? =
                    callbacks[SuccessCallback::class.java] as SuccessCallback?

                if (key == SuccessCallback::class.java) {
                    // 如果是成功的callback，就展示成功视图
                    successCallback?.show()
                } else {
                    // 如果是其他视图，先把成功视图 INVISIBLE
                    callbacks[key]?.let { successCallback?.showWithCallback(it.successViewVisible) }
                    // 再把当前callback的的视图添加到根布局上
                    val rootView = callbacks[key]?.getRootView()
                    addView(rootView)
                    callbacks[key]?.onAttach(context, rootView)
                }
                preCallback = mCallback
            }
        }
        curCallback = mCallback
    }

    /**
     * 检测callBack是否存在
     */
    private fun checkCallbackExist(callback: Class<out BaseLoadCallBack?>) {
        require(callbacks.containsKey(callback)) {
            String.format(
                "The Callback (%s) is non existent.", callback
                    .simpleName
            )
        }
    }

    companion object{
        private const val CALLBACK_CUSTOM_INDEX = 1
    }
}