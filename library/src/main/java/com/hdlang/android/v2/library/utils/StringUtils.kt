package com.hdlang.android.v2.library.utils

import android.text.TextUtils

object StringUtils {

    /**
     * 判断字符串是否为空
     * @param value
     * @return true 字符串不为空
     */
    fun isNotEmpty(value: String?): Boolean {
        if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(value?.trim())) {
            return true
        }
        return false
    }
}