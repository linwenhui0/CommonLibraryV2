package com.hdlang.android.v2.library.demo.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.reflect.TypeToken
import com.hdlang.android.v2.library.demo.R
import com.hdlang.android.v2.library.demo.model.UserBean
import com.hdlang.android.v2.library.utils.JsonUtils
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
}