package com.hdlang.android.v2.library.logic.initializer

import android.content.Context
import androidx.startup.Initializer
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * 日志初始化
 */
class LogInitializer: Initializer<Unit> {
    override fun create(context: Context) {
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}