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

class DownloadLiveDataHandler constructor(context: Context) : DownloadHandler(context = context) {


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
                                allowedNetworkTypes,
                                liveData = downloadTaskResultLiveData
                            )
                        }
                    } else {
                        deleteTask(downloadTaskResult)
                        downloadTask(
                            url,
                            saveFileName,
                            showNotification,
                            allowedOverRoaming,
                            allowedNetworkTypes,
                            liveData = downloadTaskResultLiveData
                        )
                    }
                } else {
                    deleteTask(downloadTaskResult)
                    downloadTask(
                        url,
                        saveFileName,
                        showNotification,
                        allowedOverRoaming,
                        allowedNetworkTypes,
                        liveData = downloadTaskResultLiveData
                    )
                }
            } else {
                downloadTask(
                    url,
                    saveFileName,
                    showNotification,
                    allowedOverRoaming,
                    allowedNetworkTypes,
                    liveData = downloadTaskResultLiveData
                )
            }
        }
    }





}