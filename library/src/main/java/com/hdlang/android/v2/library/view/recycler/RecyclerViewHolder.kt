package com.hdlang.android.v2.library.view.recycler

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hdlang.android.v2.library.listener.OnItemClickListener

class RecyclerViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val views = SparseArray<View?>()
    private var listener: OnItemClickListener? = null

    /**
     * 增加root view 点击事件
     */
    fun setRootListener() {
        itemView.setOnClickListener {
            val position = adapterPosition
            listener?.onItemClick(itemView, position)
        }
    }

    /**
     * 增加点击事件
     */
    fun addOnClickListener(id: Int) {
        val v = getView<View>(id)
        v?.setOnClickListener {
            val position = adapterPosition
            listener?.onItemClick(v, position)
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    fun <T : View?> getView(id: Int): T? {
        var v = views[id]
        if (v != null) {
            try {
                return v as T
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        v = itemView.findViewById(id)
        try {
            val view = v as T
            views[id] = view
            return view
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}