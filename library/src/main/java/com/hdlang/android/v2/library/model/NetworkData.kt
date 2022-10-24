package com.hdlang.android.v2.library.model

/**
 * 网络请求实体类
 */
data class NetworkData<T>(val code: String, val data: T, val message: String) : BaseNetworkData<T>()

