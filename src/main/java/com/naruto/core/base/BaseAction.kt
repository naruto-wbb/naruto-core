package com.naruto.core.base

data class BaseAction(var action: Int) {

    var mData: Any? = null

    fun addAction(action: Int): BaseAction {
        this.action = this.action or action // and
        return this
    }

    fun data(data: Any?): BaseAction {
        mData = data
        return this
    }
}