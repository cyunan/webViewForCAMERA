package com.bundletool.myapplication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AnticipateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import java.lang.Math.hypot

class WelcomeActivity : Activity() {
    private val STOP_SPLASH = 0
    private val SPLASH_TIME = 3000
    private lateinit var splashScreen: SplashScreen
    private val defaultExitDuration: Long by lazy {
        1500.toLong()
    }
    private var dialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        splashScreen = installSplashScreen()

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            theme = R.style.TestTheme
//        }
//        initView()
        setContentView(R.layout.activity_welcome)

//        showDialog()
//        splashHandler.sendMessageDelayed(Message(), SPLASH_TIME.toLong());


//        customizeSplashScreen(splashScreen)

//        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
//            showSplashIconExitAnimator1(splashScreenViewProvider.view) {
//                splashScreenViewProvider.remove()
//            }
//        }

    }
    private fun initView(){
//        window.decorView.setWINdo(R.drawable.diguo_background)
        setTheme(R.style.TestTheme)
        splashScreen = installSplashScreen()
//        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
//            showSplashIconExitAnimator1(splashScreenViewProvider.view) {
//                splashScreenViewProvider.remove()
//            }
//        }
        Thread.sleep(2000)
    }

    private fun showDialog() {
        dialog = AlertDialog.Builder(this)
            .setTitle("我是普通的对话框") //设置标题
            .setMessage("我是内容") //设置要显示的内容
            .setNegativeButton("取消", DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(this, "点击了取消按钮", Toast.LENGTH_SHORT).show()
                dialogInterface.dismiss() //销毁对话框
            })
            .setNeutralButton("第三方按钮",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(
                        this,
                        "我是第三个按钮",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            .setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "点击了确定的按钮", Toast.LENGTH_SHORT).show()
                dialog.dismiss() //销毁对话框
            }).create() //create（）方法创建对话框

        dialog?.show() //显示对话框
    }

    private fun customizeSplashScreen(splashScreen: SplashScreen) {
        customizeSplashScreenExit(splashScreen)
    }

    // Customize splash screen exit animator.
    private fun customizeSplashScreenExit(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            Log.d(
                "Splash", "SplashScreen#onSplashScreenExit view:$splashScreenViewProvider"
                        + " view:${splashScreenViewProvider.view}"
                        + " icon:${splashScreenViewProvider.iconView}"
            )

            // val onExit = {
            //     splashScreenViewProvider.remove()
            // }

            // defaultExitDuration = getRemainingDuration(splashScreenViewProvider)

            // hookViewLayout(splashScreenViewProvider)

            showSplashExitAnimator(splashScreenViewProvider.view) {
                splashScreenViewProvider.remove()
            }

            showSplashIconExitAnimator(splashScreenViewProvider.iconView) {
                splashScreenViewProvider.remove()
            }
        }
    }


    // Show exit animator for splash screen view.
    private fun showSplashExitAnimator(splashScreenView: View, onExit: () -> Unit = {}) {
        Log.d("Splash", "showSplashExitAnimator() splashScreenView:$splashScreenView" +
                " context:${splashScreenView.context}" +
                " parent:${splashScreenView.parent}")

        // Create your custom animation set.
        val alphaOut = ObjectAnimator.ofFloat(
            splashScreenView,
            View.ALPHA,
            1f,
            0f
        )

        // Slide up to center.
        val slideUp = ObjectAnimator.ofFloat(
            splashScreenView,
            View.TRANSLATION_Y,
            0f,
            // iconView.translationY,
            -(splashScreenView.height).toFloat()
        ).apply {
            addUpdateListener {
                Log.d("Splash", "showSplashIconExitAnimator() translationY:${splashScreenView.translationY}")
            }
        }

        // Slide down to center.
        val slideDown = ObjectAnimator.ofFloat(
            splashScreenView,
            View.TRANSLATION_Y,
            0f,
            // iconView.translationY,
            (splashScreenView.height).toFloat()
        ).apply {
            addUpdateListener {
                Log.d("Splash", "showSplashIconExitAnimator() translationY:${splashScreenView.translationY}")
            }
        }

        val scaleOut = ObjectAnimator.ofFloat(
            splashScreenView,
            View.SCALE_X,
            View.SCALE_Y,
            Path().apply {
                moveTo(1.0f, 1.0f)
                lineTo(0f, 0f)
            }
        )

        AnimatorSet().run {
            duration = defaultExitDuration
            interpolator = AnticipateInterpolator()
            Log.d("Splash", "showSplashExitAnimator() duration:$duration")

            // playTogether(alphaOut, slideUp)
            // playTogether(scaleOut)
            // playTogether(alphaOut)
            // playTogether(scaleOut, slideUp, alphaOut)
            // playTogether(slideUp, alphaOut)
            playTogether(slideDown, alphaOut)

            doOnEnd {
                Log.d("Splash", "showSplashExitAnimator() onEnd")
                Log.d("Splash", "showSplashExitAnimator() onEnd remove")
                onExit()
            }

            start()
        }
    }

    // Show exit animator for splash icon.
    private fun showSplashIconExitAnimator(iconView: View, onExit: () -> Unit = {}) {
        Log.d("Splash", "showSplashIconExitAnimator()" +
                " iconView[:${iconView.width}, ${iconView.height}]" +
                " translation[:${iconView.translationX}, ${iconView.translationY}]")

        val alphaOut = ObjectAnimator.ofFloat(
            iconView,
            View.ALPHA,
            1f,
            0f
        )

        // Bird scale out animator.
        val scaleOut = ObjectAnimator.ofFloat(
            iconView,
            View.SCALE_X,
            View.SCALE_Y,
            Path().apply {
                moveTo(1.0f, 1.0f)
                lineTo(0.3f, 0.3f)
            }
        )

        // Bird slide up to center.
        val slideUp = ObjectAnimator.ofFloat(
            iconView,
            View.TRANSLATION_Y,
            0f,
            // iconView.translationY,
            -(iconView.height).toFloat() * 2.25f
        ).apply {
            addUpdateListener {
                Log.d("Splash", "showSplashIconExitAnimator() translationY:${iconView.translationY}")
            }
        }

        AnimatorSet().run {
            interpolator = AnticipateInterpolator()
            duration = defaultExitDuration
            Log.d("Splash", "showSplashIconExitAnimator() duration:$duration")

            // playTogether(alphaOut, slideUp)
            playTogether(alphaOut, scaleOut, slideUp)
            // playTogether(scaleOut, slideUp)
            // playTogether(slideUp)

            doOnEnd {
                Log.d("Splash", "showSplashIconExitAnimator() onEnd remove")
                onExit()
            }

            start()
        }
    }

    private fun startSplashScreenExit(splashScreenViewProvider: SplashScreenViewProvider) {
        Log.d("Splash", "----onSplashScreenExit----")
        // splashScreenView
        val splashScreenView = splashScreenViewProvider.view
        // splashIconView
        val iconView = splashScreenViewProvider.iconView

        /**
         * ScreenView alpha 动画
         */
        val splashAlphaAnim = ObjectAnimator.ofFloat(splashScreenView, View.ALPHA, 1f, 0f)
        splashAlphaAnim.duration = 500
        splashAlphaAnim.interpolator = FastOutLinearInInterpolator()

        /**
         * iconView 向下移动的动画
         */
        val translationY = ObjectAnimator.ofFloat(
            iconView,
            View.TRANSLATION_Y,
            iconView.translationY,
            splashScreenView.height.toFloat()
        )
        translationY.duration = 500
        translationY.interpolator = FastOutLinearInInterpolator()

        // 合并渐变动画 & 下移动画
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationY, splashAlphaAnim)
        // 动画结束时调用的方法
        animatorSet.doOnEnd { splashScreenViewProvider.remove() }
        // 开启动画
        animatorSet.start()
    }

    private fun showSplashIconExitAnimator1(view: View, onExit: () -> Unit) {
        val alphaOut = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0.2f)

        val cx = view.width / 2
        val cy = view.height / 2
        // get the start radius for the clipping circle
        val startRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val revealOut = ViewAnimationUtils.createCircularReveal(
            view, cx, cy, startRadius, 0f
        )


        AnimatorSet().apply {
            duration = 1500
            interpolator = DecelerateInterpolator()
            playTogether(alphaOut, revealOut)
        }.also { it.start() }
            .doOnEnd { onExit() }

    }

    @SuppressLint("HandlerLeak")
    private val splashHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                STOP_SPLASH -> dialog?.dismiss()
                else -> {}
            }
            super.handleMessage(msg)
        }
    }


}