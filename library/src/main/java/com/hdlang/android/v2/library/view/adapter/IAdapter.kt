package com.hdlang.android.v2.library.view.adapter

import android.view.View
import android.view.ViewGroup

interface IAdapter {

    fun getView(position: Int, parent: ViewGroup): View
    fun onBindData(position: Int, v: View)
    fun getCount(): Int
}