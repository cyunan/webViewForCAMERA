package com.bundletool.myapplication.load

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bundletool.myapplication.load.callback.BaseLoadCallBack
import com.bundletool.myapplication.load.callback.SuccessCallback
import com.bundletool.myapplication.load.view.LoadLayout


class LoadService(
    targetContext: TargetContext,
    onReloadListener: BaseLoadCallBack.OnReloadListener?,
    builder: LoadServiceImpl.Builder?
) {
    private var loadLayout: LoadLayout? = null

    init {
        //1. 获取上下文
        val context: Context = targetContext.context
        //2. target转化所对应的View
        val oldContent: View = targetContext.oldContext
        //3. target转化所对应的View布局参数
        val oldLayoutParams: ViewGroup.LayoutParams = oldContent.layoutParams
        //4. 创建一个加载布局
        loadLayout = LoadLayout(context, onReloadListener)
        //5. 将SuccessCallback的 rootView 添加到loadLayout布局中
        loadLayout?.setupSuccessLayout(SuccessCallback(oldContent, context, onReloadListener))
        //6. 将 loadLayout添加到 需要替换的布局上
        targetContext.parentView.addView(loadLayout, targetContext.childIndex, oldLayoutParams)
        //7.初始化callback
        builder?.let { initCallback(it) }
    }

    /**
     * 初始化callback
     */
    private fun initCallback(builder: LoadServiceImpl.Builder) {
        //1. 获取callbacks列表
        val callbacks: List<BaseLoadCallBack> = builder.callbacks
        //2. 获取默认页面-默认是loadingCallback
        val defaultCallback: Class<out BaseLoadCallBack>? = builder.defaultCallback
        if (callbacks.isNotEmpty()) {
            for (callback in callbacks) {
                // 将callback存入loadLayout布局容器中的callbacks map集合存储起来
                loadLayout?.setupCallback(callback)
            }
        }
        defaultCallback?.let { loadLayout?.showCallback(it) }
    }

    /**
     * 设置成功页面
     */
    fun showSuccess(){
        loadLayout?.showCallback(SuccessCallback::class.java)
    }

    fun showCallback(callback: Class<out BaseLoadCallBack>){
        loadLayout?.showCallback(callback)
    }

}