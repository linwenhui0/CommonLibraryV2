package com.hdlang.android.v2.library.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object FragmentSwitch {

    fun <T : Fragment> getFragment(fragmentManager: FragmentManager, clazz: Class<T>): T? {
        val fragments = fragmentManager.fragments
        if (fragments.size > 0) {
            for (f in fragments) {
                if (f.javaClass.name == clazz.name) {
                    return f as T
                }
            }
        }
        return null
    }

    /**
     * 显示或隐藏Fragment
     */
    fun showAndHideFragment(id: Int, fragmentManager: FragmentManager, fragment: Fragment) {
        val fragmentTransition = fragmentManager.beginTransaction()
        val fragments = fragmentManager.fragments
        if (fragments.size > 0) {
            for (f in fragments) {
                fragmentTransition.hide(f)
            }
        }
        if (fragment.isAdded) {
            fragmentTransition.show(fragment)
        } else {
            fragmentTransition.add(id, fragment)
        }
        if (!fragmentTransition.isEmpty) {
            fragmentTransition.commit()
            fragmentManager.executePendingTransactions()
        }
    }

    /**
     * 替换Fragment
     */
    fun replaceFragment(id: Int, fragmentManager: FragmentManager, fragment: Fragment) {
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(id, fragment)
        if (!fragmentTransition.isEmpty) {
            fragmentTransition.commit()
            fragmentManager.executePendingTransactions()
        }
    }
}