package com.hdlang.android.v2.library.demo.logic.network

import com.hdlang.android.v2.library.logic.network.api.BaseApi
import com.hdlang.android.v2.library.logic.network.intercept.IResponseIntercept

class WeatherApi() : BaseApi() {
    override val requestClientTag: String
        get() = ""
    override val url: String
        get() = "https://www.yuanxiapi.cn/api/theweather/"

    override fun getRequestParams(): MutableMap<String, String> {
        val requestParams = HashMap<String, String>()
        requestParams["city"] = "贵阳市"
        return requestParams
    }

    override fun getNetworkIntercepts(): MutableList<IResponseIntercept> {
        return ArrayList<IResponseIntercept>()
    }


}