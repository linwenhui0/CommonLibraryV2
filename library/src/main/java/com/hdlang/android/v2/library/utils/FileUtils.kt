package com.hdlang.android.v2.library.utils

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

object FileUtils {

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

    fun delete(path: String) {
        val file = File(path)
        if (file.isDirectory) {
            val files = file.listFiles()
            for (file in files) {
                delete(file.absolutePath)
            }
        } else {
            file.delete()
        }
    }

}