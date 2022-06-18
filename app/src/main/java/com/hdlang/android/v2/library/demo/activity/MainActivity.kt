package com.hdlang.android.v2.library.demo.activity

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.hdlang.android.v2.library.demo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            1
        )
    }

    fun onJsonClick(v: View) {
        startActivity(Intent(this, JsonActivity::class.java))
    }

    fun onCacheClick(v: View) {
        startActivity(Intent(this, CacheActivity::class.java))
    }
}