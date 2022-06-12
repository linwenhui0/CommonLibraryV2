package com.hdlang.android.v2.library.demo.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hdlang.android.v2.library.demo.R
import com.hdlang.android.v2.library.utils.CacheUtils
import com.orhanobut.logger.Logger

class CacheActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cache)
    }

    fun onSaveStringClick(v: View) {
        CacheUtils.putString("userID", "林少ss123456ss")
    }

    fun onGetStringClick(v: View) {
        val userID = CacheUtils.getString("userID")
        Logger.d("userID = $userID")
    }

    fun onSaveLongClick(v: View) {
        CacheUtils.putLong("userID2", 30)
    }

    fun onGetLongClick(v: View) {
        val userID = CacheUtils.getLong("userID2")
        Logger.d("userID2 = $userID")
    }
}