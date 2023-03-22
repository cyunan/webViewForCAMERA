package com.bundletool.myapplication.load

import android.content.Context
import android.view.View

abstract class LoadCallBack {
    private var rootView: View? = null
    private var context: Context? = null
    private var onReloadListener: OnReloadListener? = null
    private var successViewVisible = false
    constructor(){}
    constructor(view: View, context: Context, onReloadListener: OnReloadListener){
        rootView = view
        this.context = context
        this.onReloadListener = onReloadListener
    }
    fun getRootView(): View?{
        val resId = onCreateView()
        // 1.如果 resId为空 且 已加载
        if (resId == 0 && rootView != null) {
            return rootView as View
        }

        // 2.如果有重写onBuildView()方法
        if (onBuildView(context) != null) {
            rootView = onBuildView(context)
        }

        if (rootView == null) {
            rootView = View.inflate(context, onCreateView(), null)
        }
        rootView?.setOnClickListener { v ->
            if (onReloadEvent(context, rootView)) {
                return@setOnClickListener
            }
            onReloadListener?.onReload(v)
        }
        onViewCreate(context, rootView)
        return rootView
    }
    protected abstract fun onCreateView(): Int

    /**
     * 创建view
     */
    protected open fun onBuildView(context: Context?): View? = null

    /**
     * 是否有刷新事件
     */
    protected open fun onReloadEvent(context: Context?, view: View?): Boolean = false

    /**
     * 在onCreateView()之后立即调用
     */
    protected open fun onViewCreate(context: Context?, view: View?) {}
}

interface OnReloadListener{
    fun onReload(view: View)
}