package com.hdlang.android.v2.library.view.recycler

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    protected val views = SparseArray<View?>()

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