package com.bundletool.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val action = findViewById<TextView>(R.id.tv_action)
        action.setOnClickListener {
            val intent = Intent()

            intent.action = Intent.ACTION_VIEW
            val content_url: Uri = Uri.parse("https://qixinweb.37.com/test1.html")
            intent.data = content_url
            startActivity(intent)
        }
    }
}