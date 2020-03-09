package com.naruto.core.net

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.naruto.core.BuildConfig
import com.naruto.core.base.app
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
            builder.cookieJar(
                PersistentCookieJar(
                    SetCookieCache(),
                    SharedPrefsCookiePersistor(app)
                )
            )
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

    protected abstract fun handleOkHttp(builder: OkHttpClient.Builder)
}