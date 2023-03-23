package com.bundletool.myapplication.load

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * 记录target对象的属性，转化为View，target父容器，target所在父容器中的索引位置
 */
data class TargetContext(
    val context: Context,
    val parentView: ViewGroup,
    val oldContext: View,
    val childIndex: Int
)
