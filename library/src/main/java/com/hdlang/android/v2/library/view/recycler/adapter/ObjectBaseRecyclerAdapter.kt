package com.hdlang.android.v2.library.view.recycler.adapter

import androidx.recyclerview.widget.RecyclerView
import com.hdlang.android.v2.library.listener.OnItemClickListener

abstract class ObjectBaseRecyclerAdapter<T, VM : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<VM>() {
    val list = ArrayList<T>()

    protected var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun get(position: Int): T? {
        try {
            return list[position]
        } catch (e: Exception) {
        }
        return null
    }


    override fun getItemCount(): Int {
        return list.size
    }
}