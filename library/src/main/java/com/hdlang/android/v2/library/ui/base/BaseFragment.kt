package com.hdlang.android.v2.library.ui.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

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

    protected fun setupToolbar(toolbar: Toolbar?) {
        if (currentActivity is AppCompatActivity) {
            val activity = currentActivity as AppCompatActivity
            activity.setSupportActionBar(toolbar)
        }
    }

    protected fun <T> findViewById(id: Int): T? {
        return view?.findViewById(id)
    }

}