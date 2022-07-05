package com.hdlang.android.v2.library.logic.network.response

import androidx.lifecycle.MutableLiveData
import com.hdlang.android.v2.library.logic.network.api.BaseApi
import com.hdlang.android.v2.library.model.BaseNetworkData
import com.hdlang.android.v2.library.model.NetworkData
import com.hdlang.android.v2.library.model.NetworkDataException
import com.hdlang.android.v2.library.model.NetworkDataIntercept
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

abstract class BaseResponse<T>(
    private val clazz: Class<T>,
    private val api: BaseApi,
    private val liveData: MutableLiveData<BaseNetworkData<T>>?,
    private val producer: ProducerScope<BaseNetworkData<T>>?
) : Callback {

    abstract fun parse(clazz: Class<T>, response: Response): BaseNetworkData<T>

    override fun onFailure(call: Call, e: IOException) {
        handlerCallback(handleException(e))
    }

    fun handleException(e: Exception): BaseNetworkData<T> {
        var intercept = false
        var networkData: BaseNetworkData<T>? = null
        val networkIntercepts = api.getNetworkIntercepts()
        if (networkIntercepts.isNotEmpty()) {
            for (networkIntercept in networkIntercepts) {
                if (networkIntercept.isIntercept(e)) {
                    intercept = true
                    GlobalScope.launch(context = Dispatchers.Main) {
                        networkIntercept.onIntercept(e)
                    }
                    networkData = NetworkDataIntercept(networkIntercept.isClosePage())
                    break
                }
            }
        }
        if (!intercept) {
            return NetworkDataException<T>(e)
        } else {
            if (networkData != null) {
                return networkData
            }
            return NetworkDataIntercept(true)
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
        handlerCallback(handleResponse(response))
    }

    private fun handlerCallback(model: BaseNetworkData<T>) {
        liveData?.postValue(model)
        if (producer != null) {
            GlobalScope.launch(context = Dispatchers.Main) {
                producer?.send(model)
            }
        }
    }

}