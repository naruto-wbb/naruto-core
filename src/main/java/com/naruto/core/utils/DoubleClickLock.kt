package com.naruto.core.utils

import com.naruto.core.utils.Ext.currentTimeMillis

object DoubleClickLock {

    private var mLastClickTime = 0L

    private const val CLICK_INTERVAL = 500L

    //是否超出点击间隔
    //距离上一次点击间隔
    //时间合法（大于0）并且距离上一次点击间隔小于间隔毫秒数（默认毫秒）则判断为连点。不触发第二次点击
    fun isBeyondClickInterval(): Boolean {
        return when (currentTimeMillis - mLastClickTime) {
            in 1 until CLICK_INTERVAL -> false
            else -> {
                mLastClickTime = currentTimeMillis
                true
            }
        }
    }

    inline fun isBeyondClickInterval(crossinline function: () -> Unit) {
        if (isBeyondClickInterval()) {
            function.invoke()
        }
    }
}
