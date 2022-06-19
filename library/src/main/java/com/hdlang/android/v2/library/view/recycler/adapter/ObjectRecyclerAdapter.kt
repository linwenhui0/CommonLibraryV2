package com.hdlang.android.v2.library.view.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hdlang.android.v2.library.view.recycler.RecyclerViewHolder
import java.util.Collection

abstract class ObjectRecyclerAdapter<T>(protected val context: Context) :
    ObjectBaseRecyclerAdapter<T, RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = LayoutInflater.from(context).inflate(viewType, parent, false)
        return RecyclerViewHolder(v)
    }
}