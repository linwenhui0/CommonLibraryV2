package com.hdlang.android.v2.library.utils

import com.tencent.mmkv.MMKV

object CacheUtils {
    private val MMKV_ID = "hmark_interprocess"
    private var mmkv: MMKV? = null

    @Synchronized
    private fun getMMKV(): MMKV {
        if (mmkv == null) {
            mmkv = MMKV.mmkvWithID(MMKV_ID, MMKV.MULTI_PROCESS_MODE, null)
        }
        return mmkv!!
    }

    fun remove(key: String) {
        if (StringUtils.isNotEmpty(key)) {
            val mmkv = getMMKV();
            mmkv.remove(key)
        }
    }

    fun getBool(key: String, def: Boolean = false): Boolean {
        if (StringUtils.isNotEmpty(key)) {
            val mmkv = getMMKV()
            return mmkv.decodeBool(key, def)
        }
        return def
    }

    fun putBool(key: String, value: Boolean) {
        if (StringUtils.isNotEmpty(key)) {
            val mmkv = getMMKV()
            mmkv.encode(key, value)
        }
    }

    fun getInt(key: String, def: Int = 0): Int {
        if (StringUtils.isNotEmpty(key)) {
            val mmkv = getMMKV()
            return mmkv.decodeInt(key, def)
        }
        return def
    }

    fun putInt(key: String, value: Int) {
        if (StringUtils.isNotEmpty(key)) {
            val mmkv = getMMKV()
            mmkv.encode(key, value)
        }
    }

    fun getLong(key: String, def: Long = 0): Long {
        if (StringUtils.isNotEmpty(key)) {
            val mmkv = getMMKV()
            return mmkv.decodeLong(key, def)
        }
        return def;
    }

    fun putLong(key: String, value: Long) {
        if (StringUtils.isNotEmpty(key)) {
            val mmkv = getMMKV()
            mmkv.encode(key, value)
        }
    }

    fun getString(key: String, def: String? = ""): String? {
        if (StringUtils.isNotEmpty(key)) {
            val mmkv = getMMKV()
            return mmkv.decodeString(key, def)
        }
        return def
    }

    fun putString(key: String, value: String) {
        if (StringUtils.isNotEmpty(key)) {
            val mmkv = getMMKV()
            mmkv.encode(key, value)
        }
    }
}