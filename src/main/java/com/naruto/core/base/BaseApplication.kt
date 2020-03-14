package com.naruto.core.base

import android.app.Application
import com.naruto.core.utils.LogUtil

lateinit var app: BaseApplication

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        app = this
        LogUtil.init()
    }
}