package com.hdlang.android.v2.library.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


fun AppCompatActivity.setupToolBar(toolbar: Toolbar?, title: String?) {
    if (toolbar != null) {
        setSupportActionBar(toolbar)
    }
    if (title?.isNotEmpty() == true) {
        toolbar?.title = title
    }
}

