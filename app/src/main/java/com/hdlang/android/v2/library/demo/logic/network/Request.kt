package com.hdlang.android.v2.library.demo.logic.network

import androidx.lifecycle.MutableLiveData
import com.hdlang.android.v2.library.logic.network.api.BaseApi
import com.hdlang.android.v2.library.logic.network.request.BaseRequest
import com.hdlang.android.v2.library.logic.network.response.BaseResponse
import com.hdlang.android.v2.library.model.BaseNetworkData
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope

class Request : BaseRequest() {
    override val timeout: Long
        get() = 30

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun <T> getResponse(
        clazz: Class<T>,
        api: BaseApi,
        liveData: MutableLiveData<BaseNetworkData<T>>?,
        producer: ProducerScope<BaseNetworkData<T>>?,
        continuation: CancellableContinuation<BaseNetworkData<T>>?
    ): BaseResponse<T> {
        return Response<T>(clazz, api, liveData, producer,continuation)
    }
}