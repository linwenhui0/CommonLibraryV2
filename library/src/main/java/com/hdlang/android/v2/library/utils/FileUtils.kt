package com.hdlang.android.v2.library.utils

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

/**
 * 文件处理
 */
object FileUtils {

    /**
     * 读取文件流
     */
    @Throws(Exception::class)
    fun readStream(input: InputStream): ByteArray {
        val output = ByteArrayOutputStream()
        val data = ByteArray(1024)
        var len = input.read(data)
        while (len > 0) {
            output.write(data, 0, len)
            len = input.read(data)
        }
        return output.toByteArray()
    }

    /**
     * 文件删除
     */
    fun delete(path: String) {
        val file = File(path)
        if (file.isDirectory) {
            val files = file.listFiles()
            if (files != null) {
                for (f in files) {
                    delete(f.absolutePath)
                }
            }
        } else {
            file.delete()
        }
    }

}