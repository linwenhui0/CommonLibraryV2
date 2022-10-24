package com.hdlang.android.v2.library.ui.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Fragment.setupToolBar(toolbar: Toolbar?, title: String?) {
    if (activity is AppCompatActivity) {
        val act = activity as AppCompatActivity
        act.setupToolBar(toolbar, title)
    }
}

fun <T> Fragment.findViewById(id: Int): T? {
    return view?.findViewById(id)
}

abstract class BaseFragment : Fragment() {

    var currentActivity: FragmentActivity? = null
        get() {
            if (field != null) {
                return field
            }
            field = activity
            return field
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentActivity = activity
    }


}