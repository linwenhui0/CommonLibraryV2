package com.hdlang.android.v2.library.view.rank.adapter

import android.database.DataSetObservable
import android.database.DataSetObserver
import com.hdlang.android.v2.library.view.adapter.IAdapter

abstract class BaseRankAdapter : IAdapter {
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
}