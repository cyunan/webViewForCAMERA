package com.bundletool.myapplication.base

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.webkit.WebView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
typealias BlankMonitorCallback = ()->Unit
class BaseWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0) : WebView(context,attrs, defStyleAttr, defStyleRes),
    LifecycleEventObserver {
    var mBlankMonitorCallback: BlankMonitorCallback? = null
    var mBlankMonitorCallback1: BlankMonitorCallback1? = null
    private val mBlankMonitorRunnable by lazy { BlankMonitorRunnable() }

    fun postBlankMonitorRunnable() {
        Log.d("TAG", "白屏检测任务 5s 后执行")
        removeCallbacks(mBlankMonitorRunnable)
        postDelayed(mBlankMonitorRunnable, 5000)
    }

    fun removeBlankMonitorRunnable() {
        Log.d("TAG", "白屏检测任务取消执行")
        removeCallbacks(mBlankMonitorRunnable)
    }


    // 省略其他代码...
    inner class BlankMonitorRunnable : Runnable {

        override fun run() {
            val task = Thread {
                // 根据宽高的 1/6 创建 bitmap
//                val dstWidth = measuredWidth / 6
//                val dstHeight = measuredHeight / 6
                val dstWidth = measuredWidth
                val dstHeight = measuredHeight
                val bmScaled = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888)
                val matrix = Matrix()
                matrix.setScale(0.2f, 0.2f)
                val snapshot = Bitmap.createBitmap(bmScaled, 0, 0, bmScaled.width, bmScaled.height, matrix, true)
                // 绘制 view 到 bitmap
                val canvas = Canvas(snapshot)
                draw(canvas)

                // 像素点总数
                val pixelCount = (snapshot.width * snapshot.height).toFloat()
                var whitePixelCount = 0 // 白色像素点计数
                var otherPixelCount = 0 // 其他颜色像素点计数
                // 遍历 bitmap 像素点
                for (x in 0 until snapshot.width) {
                    for (y in 0 until snapshot.height) {
                        // 计数 其实记录一种就可以
                        if (snapshot.getPixel(x, y) == -1) {
                            whitePixelCount++
                        }else{
                            otherPixelCount++
                        }
                    }
                }
                // 回收 bitmap
                snapshot.recycle()

                if (whitePixelCount == 0) {
                    return@Thread
                }

                // 计算白色像素点占比 （计算其他颜色也一样）
                val percentage: Float = whitePixelCount / pixelCount * 100
                // 如果超过阈值 触发白屏提醒
                if (percentage > 95) {
                    post {
                        mBlankMonitorCallback?.invoke()
                        mBlankMonitorCallback1?.onBlank()
                    }
                }else{
                    removeBlankMonitorRunnable()
                }
            }
            task.start()
        }
    }

    inner class BlankDetectionRunnable(private val view: View): Runnable{
        override fun run() {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

            isDrawingCacheEnabled = true
            val bmp = drawingCache
            val matrix = Matrix()
            matrix.setScale(0.2f, 0.2f)
            val bmScaled = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)
        }

    }


    @JvmOverloads fun registerWebViewCallBack(webViewCallBack: WebViewCallBack?, blankMonitorCallback1: BlankMonitorCallback1? = null) {
        webViewClient = BaseWebViewClient(webViewCallBack)
        mBlankMonitorCallback1 = blankMonitorCallback1
    }


    companion object {
        const val TAG = "BaseWebView"
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

    }

    interface BlankMonitorCallback1{
        fun onBlank()
    }

}