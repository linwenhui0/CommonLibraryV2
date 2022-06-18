package com.hdlang.android.v2.library.logic.common.download

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import com.hdlang.android.v2.library.model.DownloadTaskResult
import com.hdlang.android.v2.library.utils.FileUtils
import com.hdlang.android.v2.library.utils.StringUtils
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import kotlin.Exception

class DownloadHandler constructor(private val context: Context) {

    private val downloadManager: DownloadManager? by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
    }
    val downloadTaskResultLiveData: MutableLiveData<DownloadTaskResult> = MutableLiveData()

    /**
     * @param saveFileName 下载后文件保存位置
     * @param allowedOverRoaming 移动网络情况下是否允许漫游
     * @param allowedNetworkTypes 允许在何种网络下进行下载
     */
    fun download(
        url: String, saveFileName: String,
        needCheckFileMD5: Boolean = false,
        fileMD5: String = "",
        showNotification: Boolean = false,
        allowedOverRoaming: Boolean = false,
        allowedNetworkTypes: Int = DownloadManager.Request.NETWORK_WIFI
    ) {
        GlobalScope.launch(context = Dispatchers.IO) {
            val downloadTaskResult = getDownloadTask(url)
            if (downloadTaskResult != null) {
                if (downloadTaskResult.status == DownloadManager.STATUS_SUCCESSFUL) {
                    if (needCheckFileMD5) {
                        if (downloadTaskResult.md5 == fileMD5) {
                            downloadTaskResultLiveData.postValue(downloadTaskResult)
                        } else {
                            deleteTask(downloadTaskResult)
                            downloadTask(
                                url,
                                saveFileName,
                                showNotification,
                                allowedOverRoaming,
                                allowedNetworkTypes
                            )
                        }
                    } else {
                        deleteTask(downloadTaskResult)
                        downloadTask(
                            url,
                            saveFileName,
                            showNotification,
                            allowedOverRoaming,
                            allowedNetworkTypes
                        )
                    }
                } else {
                    deleteTask(downloadTaskResult)
                    downloadTask(
                        url,
                        saveFileName,
                        showNotification,
                        allowedOverRoaming,
                        allowedNetworkTypes
                    )
                }
            } else {
                downloadTask(
                    url,
                    saveFileName,
                    showNotification,
                    allowedOverRoaming,
                    allowedNetworkTypes
                )
            }
        }
    }


    /**
     * @param saveFileName 下载后文件保存位置
     * @param allowedOverRoaming 移动网络情况下是否允许漫游
     * @param allowedNetworkTypes 允许在何种网络下进行下载
     */
    private fun downloadTask(
        url: String,
        saveFileName: String,
        showNotification: Boolean = false,
        allowedOverRoaming: Boolean = false,
        allowedNetworkTypes: Int = DownloadManager.Request.NETWORK_WIFI
    ) {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedOverRoaming(allowedOverRoaming)
        request.setAllowedNetworkTypes(allowedNetworkTypes)
        if (showNotification) {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        } else {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
        }
        val saveFile =
            File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), saveFileName)
        request.setDestinationUri(Uri.fromFile(saveFile))
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        val downloadId = downloadManager?.enqueue(request)
        if (downloadId != null && downloadId > 0) {
            setupFlow(url = url, downloadId = downloadId)
        }
    }

    private fun setupFlow(url: String, downloadId: Long) {
        GlobalScope.launch(context = Dispatchers.IO) {
            while (true) {
                val downloadTaskResult = queryStatus(url = url, downloadId = downloadId)
                if (downloadTaskResult == null) {
                    break
                } else {
                    if (downloadTaskResult.status == DownloadManager.STATUS_RUNNING) {
                        downloadTaskResultLiveData.postValue(downloadTaskResult)
                        try {
                            Thread.sleep(1000)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        continue
                    } else if (downloadTaskResult.status == DownloadManager.STATUS_SUCCESSFUL) {
                        downloadTaskResultLiveData.postValue(downloadTaskResult)
                        break
                    }
                }
            }
        }
    }

    /**
     * 删除任务
     */
    private fun deleteTask(downloadTaskResult: DownloadTaskResult) {
        if (StringUtils.isNotEmpty(downloadTaskResult.fileLocalUri)) {
            val uri = Uri.parse(downloadTaskResult.fileLocalUri)
            val path = uri.path
            if (StringUtils.isNotEmpty(path)) {
                FileUtils.delete("$path")
            }
        }
        if (downloadTaskResult.id > 0) {
            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            downloadManager?.remove(downloadTaskResult.id)
        }
    }

    private fun getDownloadTask(
        url: String
    ): DownloadTaskResult? {
        var downloadTaskResult: DownloadTaskResult? = null
        val query = DownloadManager.Query()
        try {
            val cursor = downloadManager?.query(query)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    try {
                        val id =
                            cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_ID))
                        val uri =
                            cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_URI))
                        val localUri =
                            cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI))
                        val status =
                            cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                        val current =
                            cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                        val total =
                            cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                        Logger.d("uri:$uri,localUri:$localUri,status:$status,current=$current,total=$total")
                        if (url == uri) {
                            downloadTaskResult =
                                DownloadTaskResult(id = id, url = uri, status = status)
                            downloadTaskResult.fileLocalUri = localUri
                            val local = Uri.parse(localUri)
                            if (local != null && status == DownloadManager.STATUS_SUCCESSFUL) {
                                var input: InputStream? = null
                                try {
                                    input = context.contentResolver.openInputStream(local)
                                    if (input != null) {
                                        val fileDataMD5 = StringUtils.md5(input)
                                        if (StringUtils.isNotEmpty(fileDataMD5)) {
                                            downloadTaskResult.md5 = fileDataMD5
                                        }
                                    }
                                } catch (e2: Exception) {
                                    e2.printStackTrace()
                                } finally {
                                    try {
                                        input?.close()
                                    } catch (e3: Exception) {
                                        e3.printStackTrace()
                                    }
                                }
                            }
                        }
                    } catch (e1: Exception) {
                        e1.printStackTrace()
                    }
                }
            }
            cursor?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return downloadTaskResult
    }

    private fun queryStatus(url: String, downloadId: Long): DownloadTaskResult? {
        var downloadTaskResult: DownloadTaskResult? = null
        val downloadManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        try {
            val query = DownloadManager.Query()
            val cursor = downloadManager?.query(query)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    try {
                        val id =
                            cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_ID))
                        val uri =
                            cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_URI))
                        val localUri =
                            cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI))
                        val status =
                            cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                        val current =
                            cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                        val total =
                            cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                        Logger.d("uri:$uri,localUri:$localUri,status:$status,current=$current,total=$total")
                        if (url == uri || id == downloadId) {
                            downloadTaskResult = DownloadTaskResult(
                                id = id,
                                url = uri,
                                status = status
                            )
                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                downloadTaskResult.fileLocalUri = localUri
                            }
                            downloadTaskResult.current = current
                            downloadTaskResult.total = total
                            break
                        }
                    } catch (e1: Exception) {
                        e1.printStackTrace()
                    }
                }
            }
            cursor?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return downloadTaskResult
    }

}