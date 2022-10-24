package com.hdlang.android.v2.library.view.rank.adapter

import android.database.DataSetObservable
import android.database.DataSetObserver
import android.view.View
import com.hdlang.android.v2.library.view.adapter.IAdapter

/**
 * 排行版处理
 */
abstract class BaseRankAdapter<T> : IAdapter {

    protected val list = ArrayList<T>()


    fun get(position: Int): T? {
        try {
            return list[position]
        } catch (e: Exception) {
        }
        return null
    }

    private val mDataSetObservable: DataSetObservable by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        DataSetObservable()
    }

    fun registerDataSetObserver(observer: DataSetObserver) {
        mDataSetObservable.registerObserver(observer)
    }

    fun unregisterDataSetObserver(observer: DataSetObserver) {
        mDataSetObservable.unregisterObserver(observer)
    }

    fun notifyDataSetChanged() {
        mDataSetObservable.notifyChanged()
    }

    fun notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated()
    }

    override fun getCount(): Int {
        return list.size
    }

}