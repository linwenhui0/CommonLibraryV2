package com.hdlang.android.v2.library.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ImageUtils {

    fun load(url: String?, v: ImageView, placeholder: Int? = 0, error: Int? = 0) {
        if (StringUtils.isNotEmpty(url)) {
            val options = RequestOptions()
            if (placeholder != null && placeholder > 0) {
                options.placeholder(placeholder)
            }
            if (error != null && error > 0) {
                options.error(error)
            }
            Glide.with(v).load(url).apply(options).into(v)
        }
    }

    fun load(url: String?, v: ImageView, placeholder: Drawable?, error: Drawable?) {
        if (StringUtils.isNotEmpty(url)) {
            val options = RequestOptions()
            if (placeholder != null) {
                options.placeholder(placeholder)
            }
            if (error != null) {
                options.error(error)
            }
            Glide.with(v).load(url).apply(options).into(v)
        }
    }

}