package com.hdlang.android.v2.library.demo.activity

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.reflect.TypeToken
import com.hdlang.android.v2.library.demo.R
import com.hdlang.android.v2.library.demo.logic.network.Request
import com.hdlang.android.v2.library.demo.logic.network.WeatherApi
import com.hdlang.android.v2.library.demo.model.UserBean
import com.hdlang.android.v2.library.logic.common.download.DownloadFlowHandler
import com.hdlang.android.v2.library.logic.common.download.DownloadLiveDataHandler
import com.hdlang.android.v2.library.model.NetworkData
import com.hdlang.android.v2.library.utils.ImageUtils
import com.hdlang.android.v2.library.utils.JsonUtils
import com.hdlang.android.v2.library.utils.StringUtils
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class JsonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)
    }

    fun onParseObjectClick(v: View) {}

    fun onParseListClick(v: View) {
        val array = JSONArray()
        array.put(
            createUserBeanData(
                name = "zhangsan",
                age = "10",
                phone = "1111",
                email = "1111@11.com"
            )
        )
        array.put(
            createUserBeanData(
                name = "lisi",
                age = "20",
                phone = "2222",
                email = "2222@22.com"
            )
        )
        println(array.toString())
        val list = JsonUtils.parseObject(array.toString(), object : TypeToken<List<UserBean>>() {})
        println(list?.size)
        println(list?.get(0))
    }

    fun onPostClick(v: View) {
//            val api = WeatherApi()
//            val request = Request()
//            request.asyncWithLiveData(String::class.java, api).observe(this) {
//                if (it is NetworkData) {
//                    Logger.i("code(${it.code}) data(${it.data})")
//                }
//            }
////        }
        GlobalScope.launch(context = Dispatchers.IO) {
            val api = WeatherApi()
            val request = Request()
//            val it = request.asyncWithSuspend(String::class.java, api)
//            if (it is NetworkData) {
//                Logger.i("code(${it.code}) data(${it.data})")
//            }
//            val it = request.sync(String::class.java,api)
//            if (it is NetworkData){
//                Logger.i("code(${it.code}) data(${it.data})")
//            }

            request.asyncWithFlow(String::class.java, api).collect {

                if (it is NetworkData) {
                    Logger.i("code(${it.code}) data(${it.data})")
                }
            }
        }
    }

    private fun createUserBeanData(
        name: String,
        age: String,
        phone: String,
        email: String
    ): JSONObject {
        val obj = JSONObject()
        obj.put("name", name)
        obj.put("age", age)
        obj.put("phone", phone)
        obj.put("email", email)
        return obj
    }

    fun onLoadImageClick(v: View) {
        val image = findViewById<ImageView>(R.id.image)
        ImageUtils.load(
            url = "https://t7.baidu.com/it/u=3676218341,3686214618&fm=193&f=GIF",
            v = image
        )
    }

    @OptIn(FlowPreview::class)
    fun onWechatDownloadClick(v: View) {
        val url =
            "https://5432f2ec4991d23431f0859c8d9730f4.rdt.tfogc.com:49156/dldir1.qq.com/weixin/android/weixin8023android2160_arm64_1.apk?mkey=62a5972525de8780560410fc0c252385&arrive_key=262185570182&cip=112.49.232.227&proto=https"
            val downloadLiveDataHandler = DownloadLiveDataHandler(this)
            downloadLiveDataHandler.downloadTaskResultLiveData.observe(this) {
                if (it.status == DownloadManager.STATUS_SUCCESSFUL && StringUtils.isNotEmpty(it.fileLocalUri)) {
                    val uri = Uri.parse(it.fileLocalUri)
                    Logger.i("file = ${uri.path}")
                }
                Logger.i("status = ${it.status} , url = ${it.url} , file = ${it.fileLocalUri} , progress = ${it.getProgress()}")
            }
            downloadLiveDataHandler.download(
                url = url,
                saveFileName = "wechat.apk"
            )

//        GlobalScope.launch(context = Dispatchers.IO) {
//            val downloadLiveDataHandler = DownloadFlowHandler(application)
//            downloadLiveDataHandler.download(
//                url = url,
//                saveFileName = "wechat.apk"
//            ).debounce(2000).collect {
//                if (it.status == DownloadManager.STATUS_SUCCESSFUL && StringUtils.isNotEmpty(it.fileLocalUri)) {
//                    val uri = Uri.parse(it.fileLocalUri)
//                    Logger.i("file = ${uri.path}")
//                }
//                Logger.i("status = ${it.status} , url = ${it.url} , file = ${it.fileLocalUri} , progress = ${it.getProgress()}")
//
//            }
//        }
    }
}