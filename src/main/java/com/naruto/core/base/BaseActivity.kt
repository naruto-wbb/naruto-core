package com.naruto.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Activity基类
 */
@ExperimentalCoroutinesApi
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayout())
        initView()
        initData()
    }

    @ExperimentalCoroutinesApi
    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    abstract fun getLayout(): Int

    abstract fun initView()
    abstract fun initData()
}