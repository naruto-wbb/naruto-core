package com.naruto.core.utils

import com.naruto.core.BuildConfig
import timber.log.Timber

/**
 * Log工具类 内部实现使用第三方SDK
 */
object LogUtil {

    fun init() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    @JvmStatic
    fun i(message: String, vararg args: Any?) {
        Timber.i(message, args)
    }

    @JvmStatic
    fun d(message: String, vararg args: Any?) {
        Timber.d(message, args)
    }

    @JvmStatic
    fun w(message: String, vararg args: Any?) {
        Timber.w(message, args)
    }

    @JvmStatic
    fun e(message: String, vararg args: Any?) {
        Timber.e(message, args)
    }

    @JvmStatic
    fun e(throwable: Throwable) {
        Timber.e(throwable)
    }
}

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

    }
}