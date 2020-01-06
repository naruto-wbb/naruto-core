package com.naruto.core.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.naruto.core.base.app
import timber.log.Timber

object UIUtil {

    const val TRANSPARENT_LOADING = -1

    var defaultResId: Int = TRANSPARENT_LOADING

    /**
     * 获取glide加载配置
     */
    fun getGlideOption(
        skipMemoryCache: Boolean = false
    ): RequestOptions {
        val diskCacheStrategy = RequestOptions()
            .skipMemoryCache(skipMemoryCache)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

        if (defaultResId != TRANSPARENT_LOADING) {
            return diskCacheStrategy
                .error(defaultResId)
                .fallback(defaultResId)
                .placeholder(defaultResId)
        }
        return diskCacheStrategy
    }

    fun loadImg(
        obj: Any?,
        iv: ImageView?,
        imgUrl: String?,
        skipMemoryCache: Boolean = false
    ): ImageView? {
        if (obj is Fragment? || obj is Context?) {
            if (iv != null) {
                if (!TextUtils.isEmpty(imgUrl) && obj != null) {
                    val options = getGlideOption(skipMemoryCache)

                    try {
                        val requestManager = when (obj) {
                            is Fragment -> Glide.with(obj)
                            is Context -> Glide.with(obj)
                            else -> throw IllegalArgumentException("Glide with( context or fragment ) ")
                        }
                        requestManager.load(imgUrl)
                            .apply(options)
                            .into(iv)
                    } catch (e: Exception) {
                        iv.setImageDrawable(app.resources.getDrawable(defaultResId))
                    }
                } else {
                    iv.setImageDrawable(app.resources.getDrawable(defaultResId))
                }
            }
        }
        return iv
    }

    fun getStatusBarHeight(): Int {
        return try {
            val resources = app.resources
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            resources.getDimensionPixelSize(resourceId)
        } catch (e: Exception) {
            Timber.e("getStatusBarHeight error = $e")
            0
        }
    }

    fun setImmerseLayout(context: Activity) {// view为标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = context.window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    fun loadLocalImg(obj: Any?, iv: ImageView?, resId: Int) {
        iv?.let {
            try {
                val options = getGlideOption()
                val glide = when (obj) {
                    is Fragment -> Glide.with(obj)
                    is Context -> Glide.with(obj)
                    is View -> Glide.with(obj)
                    else -> return
                }
                glide.load(resId).apply(options).into(it)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}