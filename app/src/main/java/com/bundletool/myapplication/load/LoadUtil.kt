package com.bundletool.myapplication.load

import android.R
import android.app.Activity
import android.content.Context
import android.os.Looper
import android.view.View
import android.view.ViewGroup

object LoadUtil {
    fun getTargetContent(target: Any?): TargetContext{
        val contentParent: ViewGroup
        val context: Context
        when (target) {
            is View -> {
                contentParent = target.parent as ViewGroup
                context = target.context
            }
            is Activity -> {
                context = target
                contentParent = target.findViewById<View>(R.id.content) as ViewGroup
            }
            else -> {
                throw IllegalArgumentException("The target must be View.")
            }
        }
        //如果有父容器，则获取父容器中的子视图个数，如果没有父容器，则父容器中子视图个数为0
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
        if (oldContent == null) {
            //如果target对应的视图为null，则抛出异常
            throw IllegalArgumentException(String.format("enexpected error when register LoadSir in %s", target.javaClass.simpleName))
        }
        return TargetContext(context, contentParent, oldContent, childIndex)
    }

    fun isMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper()
}

