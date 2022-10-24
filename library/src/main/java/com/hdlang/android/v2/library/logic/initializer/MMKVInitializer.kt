package com.hdlang.android.v2.library.logic.initializer

import android.content.Context
import androidx.startup.Initializer
import com.getkeepsafe.relinker.ReLinker
import com.orhanobut.logger.Logger
import com.tencent.mmkv.*
import java.io.File

/**
 * 初始化MMKV
 */
class MMKVInitializer : Initializer<Unit>, MMKVHandler, MMKVContentChangeNotification {
    override fun create(context: Context) {
        val dir = File(context.filesDir, "mmkv").absolutePath
        val rootDir = MMKV.initialize(
            context, dir,
            { libName -> ReLinker.loadLibrary(context, libName) }, MMKVLogLevel.LevelInfo
        )
        MMKV.registerHandler(this)
        MMKV.registerContentChangeNotify(this)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(LogInitializer::class.java)
    }

    override fun onMMKVCRCCheckFail(mmapID: String?): MMKVRecoverStrategic {
        return MMKVRecoverStrategic.OnErrorRecover
    }

    override fun onMMKVFileLengthError(mmapID: String?): MMKVRecoverStrategic {
        return MMKVRecoverStrategic.OnErrorRecover
    }

    override fun wantLogRedirecting(): Boolean {
        return true
    }

    override fun mmkvLog(
        level: MMKVLogLevel?,
        file: String?,
        line: Int,
        function: String?,
        message: String?
    ) {
        val log = "<$file:$line::$function> $message"
        when (level) {
            MMKVLogLevel.LevelDebug -> Logger.d(log)
            MMKVLogLevel.LevelNone, MMKVLogLevel.LevelInfo -> Logger.i(log)
            MMKVLogLevel.LevelWarning -> Logger.w(log)
            MMKVLogLevel.LevelError -> Logger.e(log)
        }
    }

    override fun onContentChangedByOuterProcess(mmapID: String?) {
        Logger.i("other process has changed content of : $mmapID")
    }
}