package com.hdlang.android.v2.library.model

data class NetworkData<T>(val code: String, val data: T, val message: String) : BaseNetworkData<T>()

