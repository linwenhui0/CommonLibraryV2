package com.hdlang.android.v2.library.logic.network.response

import androidx.lifecycle.MutableLiveData
import com.hdlang.android.v2.library.logic.network.api.BaseApi
import com.hdlang.android.v2.library.model.BaseNetworkData
import com.hdlang.android.v2.library.model.NetworkData
import com.hdlang.android.v2.library.model.NetworkDataException
import com.hdlang.android.v2.library.model.NetworkDataIntercept
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

abstract class BaseResponse<T>(
    private val clazz: Class<T>,
    private val api: BaseApi,
    private val liveData: MutableLiveData<BaseNetworkData<T>>?
) : Callback {

    abstract fun parse(clazz: Class<T>, response: Response): BaseNetworkData<T>

    override fun onFailure(call: Call, e: IOException) {
        handleException(e)
    }

    fun handleException(e: Exception) {
        var intercept = false
        val networkIntercepts = api.getNetworkIntercepts()
        if (networkIntercepts.isNotEmpty()) {
            for (networkIntercept in networkIntercepts) {
                if (networkIntercept.isIntercept(e)) {
                    intercept = true
                    networkIntercept.onIntercept(e)
                    liveData?.postValue(NetworkDataIntercept(networkIntercept.isClosePage()))
                    break
                }
            }
        }
        if (!intercept) {
            val mode = NetworkDataException<T>(e)
            liveData?.postValue(mode)
        }
    }

    fun handleResponse(response: Response): BaseNetworkData<T> {
        val networkData = parse(clazz, response)
        if (networkData is NetworkData) {
            val networkIntercepts = api.getNetworkIntercepts()
            for (networkIntercept in networkIntercepts) {
                if (networkIntercept.isIntercept(networkData)) {
                    networkIntercept.onIntercept(networkData)
                    return NetworkDataIntercept(networkIntercept.isClosePage())
                }
            }
        }
        return networkData
    }

    override fun onResponse(call: Call, response: Response) {
        val model = handleResponse(response)
        liveData?.postValue(model)
    }

}