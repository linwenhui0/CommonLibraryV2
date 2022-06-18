package com.hdlang.android.v2.library.model

data class NetworkDataException<T>(private val e: Exception) : BaseNetworkData<T>()
