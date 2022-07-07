package com.hdlang.android.v2.library.demo.logic.network

import androidx.lifecycle.MutableLiveData
import com.hdlang.android.v2.library.logic.network.api.BaseApi
import com.hdlang.android.v2.library.logic.network.response.BaseResponse
import com.hdlang.android.v2.library.model.BaseNetworkData
import com.hdlang.android.v2.library.model.NetworkData
import com.hdlang.android.v2.library.model.NetworkDataException
import com.hdlang.android.v2.library.model.NetworkException
import com.hdlang.android.v2.library.utils.JsonUtils
import com.hdlang.android.v2.library.utils.StringUtils
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.FlowCollector
import okhttp3.Response

class Response<T>(
    clazz: Class<T>,
    api: BaseApi,
    liveData: MutableLiveData<BaseNetworkData<T>>?,
    producer: ProducerScope<BaseNetworkData<T>>?,
    continuation: CancellableContinuation<BaseNetworkData<T>>?
) : BaseResponse<T>(clazz, api, liveData, producer, continuation) {
    override fun parse(clazz: Class<T>, response: Response): BaseNetworkData<T> {
        if (response.isSuccessful) {
            val body = response.body?.string()
            if (StringUtils.isNotEmpty(body)) {
                if (clazz == String::class.java) {
                    return NetworkData<T>(
                        code = "1",
                        data = body as T,
                        message = ""
                    )
                } else {
                    val data = JsonUtils.parseObject(body, clazz)
                    if (data != null) {
                        return NetworkData<T>(
                            code = "1",
                            data = data,
                            message = ""
                        )
                    }
                }
            }
        }
        return NetworkDataException<T>(NetworkException())


    }
}