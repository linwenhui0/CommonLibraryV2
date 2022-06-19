package com.hdlang.android.v2.library.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {
    fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }
}