package com.hdlang.android.v2.library.logic.network.intercept

import com.hdlang.android.v2.library.model.NetworkData

/**
 * 响应拦截器
 */
interface IResponseIntercept {

    /**
     * 是否拦截
     */
    fun isIntercept(networkData: NetworkData<*>): Boolean

    /**
     * 是否拦截
     */
    fun isIntercept(e: Exception): Boolean

    /**
     * 是否需要关闭界面
     */
    fun isClosePage(): Boolean

    /**
     * 拦截处理
     */
    fun onIntercept(networkData: NetworkData<*>)

    /**
     * 拦截处理
     */
    fun onIntercept(e: Exception)
}