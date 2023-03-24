package com.bundletool.myapplication.load.callback

import android.content.Context
import android.view.View
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

abstract class BaseLoadCallBack: Serializable {
    private var rootView: View? = null
    private var context: Context? = null
    private var onReloadListener: OnReloadListener? = null
    var successViewVisible: Boolean = false
    constructor()
    constructor(view: View, context: Context, onReloadListener: OnReloadListener?){
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

    fun obtainRootView(): View?{
        if (rootView == null){
            rootView = getRootView()
        }
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

    /**
     * 当 Callback 的 rootView 附加到其 LoadLayout 时调用
     */
    fun onAttach(context: Context?, view: View?){}

    /**
     * 当 Callback 的 rootView 从其 LoadLayout 中移除时调用。
     */
    fun onDetach(){}

    fun copy(): BaseLoadCallBack? {
        val bao = ByteArrayOutputStream()
        val oos: ObjectOutputStream
        var obj: Any? = null
        try {
            oos = ObjectOutputStream(bao)
            oos.writeObject(this)
            oos.close()
            val bis = ByteArrayInputStream(bao.toByteArray())
            val ois = ObjectInputStream(bis)
            obj = ois.readObject()
            ois.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return obj as BaseLoadCallBack?
    }

    fun setCallback(view: View?, context: Context?, onReloadListener: OnReloadListener?): BaseLoadCallBack{
        rootView = view
        this.context = context
        this.onReloadListener = onReloadListener
        return this
    }


    interface OnReloadListener{
        fun onReload(view: View)
    }
}

