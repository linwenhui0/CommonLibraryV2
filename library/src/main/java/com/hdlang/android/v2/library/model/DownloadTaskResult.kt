package com.hdlang.android.v2.library.model

import android.app.DownloadManager
import com.hdlang.android.v2.library.utils.constants.DownloadConstants

/**
 * 下载进度实体类
 */
data class DownloadTaskResult(val id: Long, val url: String, val status: Int) {
    var fileLocalUri: String = ""
    var md5: String = ""
    var current: Long = 0
    var total: Long = 0

    @Synchronized
    fun getProgress(): Double {
        if (status == DownloadManager.STATUS_SUCCESSFUL) {
            return 1.0
        }
        if (total > current) {
            return current.toDouble() / total
        }
        return 0.0
    }

    fun toMap(): Map<String, Any> {
        val dataMap = HashMap<String, Any>()
        dataMap[DownloadConstants.KEY_DOWNLOAD_ID] = id
        dataMap[DownloadConstants.KEY_DOWNLOAD_URL] = url
        dataMap[DownloadConstants.KEY_DOWNLOAD_STATUS] = status
        dataMap[DownloadConstants.KEY_DOWNLOAD_FILE_LOCAL_URL] = fileLocalUri
        dataMap[DownloadConstants.KEY_DOWNLOAD_CURRENT] = current
        dataMap[DownloadConstants.KEY_DOWNLOAD_TOTAL] = total
        dataMap[DownloadConstants.KEY_DOWNLOAD_FILE_MD5] = md5
        return dataMap
    }
}
