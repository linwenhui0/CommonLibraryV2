package com.hdlang.android.v2.library.view.rank.adapter

import android.database.DataSetObservable
import android.database.DataSetObserver
import android.view.View
import com.hdlang.android.v2.library.view.adapter.IAdapter

abstract class BaseRankAdapter<T> : IAdapter {
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

    fun get(position: Int): T? {
        try {
            return list[position]
        } catch (e: Exception) {
        }
        return null
    }

    private val mDataSetObservable: DataSetObservable by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
        DataSetObservable()
    }
    fun registerDataSetObserver(observer: DataSetObserver) {
        mDataSetObservable.registerObserver(observer)
    }
    fun unregisterDataSetObserver(observer: DataSetObserver) {
        mDataSetObservable.unregisterObserver(observer)
    }

    fun notifyDataSetChanged(){
        mDataSetObservable.notifyChanged()
    }

    fun notifyDataSetInvalidated(){
        mDataSetObservable.notifyInvalidated()
    }

    override fun getCount(): Int {
        return list.size
    }

}