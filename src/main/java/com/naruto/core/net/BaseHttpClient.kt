package com.naruto.core.net

import com.naruto.core.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseHttpClient {

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(logging)
            }
            handleOkHttp(builder)
            return builder.build()
        }

    private fun retrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createService(baseUrl: String, service: Class<T>): T {
        return retrofit(baseUrl).create(service)
    }

    /**
     * 子类实现加入全局操作 如添加公共参数
     */
    protected abstract fun handleOkHttp(builder: OkHttpClient.Builder)
}