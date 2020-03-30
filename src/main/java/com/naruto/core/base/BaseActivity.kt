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
abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity(),
    CoroutineScope by MainScope() {

    lateinit var mViewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getLayout() > 0) setContentView(getLayout())
        initViewModel()
        setBaseViewModel(this::mViewModel.isInitialized)
        initView()
        initData()
    }

    protected open fun setBaseViewModel(initialized: Boolean) {
    }

    @ExperimentalCoroutinesApi
    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    abstract fun getLayout(): Int
    abstract fun initViewModel()
    abstract fun initView()
    abstract fun initData()
}