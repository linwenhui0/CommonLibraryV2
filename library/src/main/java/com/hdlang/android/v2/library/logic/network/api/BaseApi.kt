package com.hdlang.android.v2.library.logic.network.api

import com.hdlang.android.v2.library.logic.network.intercept.IResponseIntercept

abstract class BaseApi {


    abstract val requestClientTag: String

    // 请求地址
    abstract val url: String

    // 请求参数
    abstract fun getRequestParams():MutableMap<String, String>

    // 拦截器
    abstract fun getNetworkIntercepts(): MutableList<IResponseIntercept>

}
