package com.hdlang.android.v2.library.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * json转换工具
 */
object JsonUtils {

    fun <T> parseObject(value: String?, clazz: Class<T>): T? {
        if (StringUtils.isNotEmpty(value)) {
            val gson = Gson()
            return gson.fromJson(value, clazz)
        }
        return null
    }

    fun <T> parseObject(value: String?, typeToken: TypeToken<T>): T? {
        if (StringUtils.isNotEmpty(value)) {
            val gson = Gson()
            return gson.fromJson(value, typeToken.type)
        }
        return null
    }

    fun toJson(value: Any?): String? {
        if (value != null) {
            val gson = Gson()
            return gson.toJson(value)
        }
        return null
    }

}