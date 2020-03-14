package com.naruto.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    lateinit var mViewModel: T

    private var mIsFirstLoad = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    override fun onResume() {
        super.onResume()

        if (mIsFirstLoad) {
            initView()
            initData()
            mIsFirstLoad = false
        }
    }

    abstract fun initViewModel()
    abstract fun initData()
    abstract fun initView()
    abstract fun getLayout(): Int
}