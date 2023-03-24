package com.bundletool.myapplication.load

import android.app.Activity
import android.content.Context
import android.os.Looper
import android.view.View
import android.view.ViewGroup

object LoadUtil {
    /**
     * 主要功能
     * 1. 将当前页面展示的布局 从父容器中 移除
     * 2. 封装TargetContext
     *
     */
    fun getTargetContent(target: Any?): TargetContext{
        //1. 获取上下文和viewGroup
        val contentParent: ViewGroup
        val context: Context
        when (target) {
            is View -> {
                contentParent = target.parent as ViewGroup
                context = target.context
            }
            is Activity -> {
                context = target
                contentParent = target.findViewById<View>(android.R.id.content) as ViewGroup
            }
            else -> {
                throw IllegalArgumentException("The target must be View.")
            }
        }

        //2. 如果有父容器，则获取父容器中的子视图个数，如果没有父容器，则父容器中子视图个数为0
        val childCount = contentParent.childCount ?: 0
        val oldContent: View
        var childIndex = 0
        if (target is View){
            oldContent = target
            for (i in 0 until childCount) {
                if (contentParent.getChildAt(i) === oldContent) {
                    childIndex = i
                    break
                }
            }
        }else{
            // 如果target为 activity
            // 父容器不为空，则传入的target所对应的视图为父容器中第一个子视图，否则target对应的视图就为null。
            oldContent = contentParent.getChildAt(0)
        }
        // 如果target对应的视图为null，则抛出异常
        if (oldContent == null) {
            throw IllegalArgumentException(String.format("unexpected error when register LoadSir in %s", target.javaClass.simpleName))
        }

        // 将父容器中的target所对应的视图移除
        contentParent.removeView(oldContent)
        return TargetContext(context, contentParent, oldContent, childIndex)
    }

    fun isMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper()
}

