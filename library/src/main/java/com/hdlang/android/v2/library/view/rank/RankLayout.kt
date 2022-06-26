package com.hdlang.android.v2.library.view.rank

import android.content.Context
import android.database.DataSetObserver
import android.util.AttributeSet
import android.widget.LinearLayout
import com.hdlang.android.v2.library.view.rank.adapter.BaseRankAdapter

/**
 * 排行版排序
 */
class RankLayout : LinearLayout {
    private var adapter: BaseRankAdapter<*>? = null
    private val observer = object : DataSetObserver() {
        override fun onChanged() {
            super.onChanged()
            onForceChanged()
        }

        override fun onInvalidated() {
            super.onInvalidated()
            onForceInvalidated()
        }
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        orientation = HORIZONTAL
    }

    fun <T> setAdapter(adapter: BaseRankAdapter<T>) {
        this.adapter?.unregisterDataSetObserver(observer)
        adapter.registerDataSetObserver(observer)
        this.adapter = adapter
        onForceInvalidated()
    }

    fun onForceChanged() {
        val adapter = this.adapter
        if (adapter != null) {
            val len = adapter.getCount()
            for (index in 0 until len) {
                if (index < childCount) {
                    val view = getChildAt(index)
                    adapter.onBindData(index, view)
                }
            }
        }
    }

    fun onForceInvalidated() {
        removeAllViews()
        val adapter = this.adapter
        if (adapter != null) {
            val len = adapter.getCount()
            for (index in 0 until len) {
                val view = adapter.getView(index, this)
                val params = LayoutParams(
                    0,
                    LayoutParams.MATCH_PARENT
                )
                params.weight = 1f
                addView(view, params)
                adapter.onBindData(index, view)
            }
        }
    }
}