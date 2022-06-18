package com.hdlang.android.v2.library.utils

import android.text.TextUtils
import java.io.FileInputStream
import java.io.InputStream
import java.security.MessageDigest

object StringUtils {

    fun isEmpty(value: String?): Boolean {
        return !isNotEmpty(value)
    }

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

    /**
     * 转换成16进制字符串
     */
    fun toHexString(array: ByteArray): String {
        val builder = StringBuilder()
        val len = array.size
        for (index in 0 until len) {
            builder.append(String.format("%2d", index).replace(" ", "0"))
        }
        return builder.toString()
    }

    private fun digest(algorithm: String, input: InputStream): String {
        try {
            val messageDigest = MessageDigest.getInstance(algorithm)
            val tmp = ByteArray(1024)
            var len = input.read(tmp)
            while (len > 0) {
                messageDigest.update(tmp, 0, len)
                len = input.read(tmp)
            }
            val data = messageDigest.digest()
            return toHexString(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun digest(algorithm: String, value: ByteArray): String {
        try {
            val messageDigest = MessageDigest.getInstance(algorithm)
            messageDigest.update(value)
            val data = messageDigest.digest()
            return toHexString(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun md5(value: ByteArray): String {
        return digest("MD5", value)
    }

    fun md5(input: InputStream): String {
        return digest("MD5", input)
    }
}