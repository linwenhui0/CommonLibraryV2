package com.hdlang.android.v2.library.logic.network.request

import androidx.lifecycle.MutableLiveData
import com.hdlang.android.v2.library.logic.network.api.BaseApi
import com.hdlang.android.v2.library.logic.network.response.BaseResponse
import com.hdlang.android.v2.library.model.BaseNetworkData
import com.hdlang.android.v2.library.utils.StringUtils
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import okhttp3.*
import java.util.concurrent.TimeUnit

abstract class BaseRequest {
    companion object {
        private val clients = HashMap<String, OkHttpClient?>()
    }

    protected abstract val timeout: Long

    private fun getOkhttpClient(tag: String): OkHttpClient {
        if (StringUtils.isNotEmpty(tag)) {
            var client = clients[tag]
            return if (client != null) {
                client
            } else {
                client = createOkhttpClient(tag)
                clients[tag] = client
                client
            }
        }
        return createOkhttpClient(tag)
    }

    private fun createOkhttpClient(tag: String): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .readTimeout(timeout, TimeUnit.MINUTES)
            .writeTimeout(timeout, TimeUnit.MINUTES)
            .connectTimeout(timeout, TimeUnit.MINUTES)
            .callTimeout(timeout, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
        val interceptors = getInterceptors(tag)
        if (interceptors?.isNotEmpty() == true) {
            for (interceptor in interceptors) {
                clientBuilder.addInterceptor(interceptor)
            }
        }
        return clientBuilder.build()
    }

    protected fun getInterceptors(tag: String): MutableList<Interceptor>? {
        return null
    }

    private fun request(api: BaseApi): Call {
        val client = getOkhttpClient(api.requestClientTag)
        val requestBodyBuilder = FormBody.Builder()
        val requestParams = api.getRequestParams()
        if (requestParams.isNotEmpty()) {
            for (entry in requestParams.entries) {
                requestBodyBuilder.add(entry.key, entry.value)
            }
        }
        val request = Request.Builder()
            .url(api.url)
            .post(requestBodyBuilder.build())
            .build()
        return client.newCall(request)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    abstract fun <T> getResponse(
        clazz: Class<T>,
        api: BaseApi,
        liveData: MutableLiveData<BaseNetworkData<T>>? = null,
        producer: ProducerScope<BaseNetworkData<T>>? = null,
        continuation: CancellableContinuation<BaseNetworkData<T>>? = null
    ): BaseResponse<T>

    @OptIn(ExperimentalCoroutinesApi::class)
    fun <T> asyncWithLiveData(
        clazz: Class<T>,
        api: BaseApi
    ): MutableLiveData<BaseNetworkData<T>> {
        val liveData = MutableLiveData<BaseNetworkData<T>>()
        val call = request(api)
        call.enqueue(getResponse(clazz = clazz, api = api, liveData = liveData))
        return liveData
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun <T> asyncWithFlow(clazz: Class<T>, api: BaseApi): Flow<BaseNetworkData<T>> = callbackFlow {
        val call = request(api)
        call.enqueue(getResponse(clazz = clazz, api = api, producer = this))
        awaitClose {
        }
    }.flowOn(context = Dispatchers.IO)

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun <T> asyncWithSuspend(clazz: Class<T>, api: BaseApi): BaseNetworkData<T> {
        return suspendCancellableCoroutine {
            val call = request(api)
            call.enqueue(getResponse(clazz = clazz, api = api, continuation = it))
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    fun <T> sync(clazz: Class<T>, api: BaseApi): BaseNetworkData<T>? {
        val call = request(api)
        val responseHandler = getResponse(clazz, api)
        return try {
            val response = call.execute()
            responseHandler.handleResponse(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}