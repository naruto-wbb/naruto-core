package com.naruto.core.base

import android.app.Application
import com.naruto.core.utils.LogUtil

lateinit var app: BaseApplication

fun getString(resId: Int, vararg args: Any?): String {
    return app.resources.getString(resId, args)
}

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        app = this
        LogUtil.init()
    }
}