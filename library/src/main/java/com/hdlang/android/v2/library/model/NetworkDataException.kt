package com.hdlang.android.v2.library.model

/**
 * 网络连接异常
 */
data class NetworkDataException<T>(private val e: Exception) : BaseNetworkData<T>()
