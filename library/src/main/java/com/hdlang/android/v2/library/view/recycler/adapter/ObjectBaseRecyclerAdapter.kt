package com.hdlang.android.v2.library.view.recycler.adapter

import androidx.recyclerview.widget.RecyclerView
import java.util.Collection

abstract class ObjectBaseRecyclerAdapter<T, VM : RecyclerView.ViewHolder> : RecyclerView.Adapter<VM>() {
    protected val list = ArrayList<T>()

    fun add(data: T): Boolean {
        return list.add(data)
    }

    fun add(index: Int, data: T) {
        list.add(index, data)
    }

    fun remove(data: T): Boolean {
        return list.remove(data)
    }

    fun remove(index: Int): T {
        return list.removeAt(index)
    }

    fun addAll(c: Collection<T>): Boolean {
        return list.addAll(c)
    }

    fun clear() {
        list.clear()
    }


    override fun getItemCount(): Int {
        return list.size
    }
}