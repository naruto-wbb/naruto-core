package com.naruto.core.base

val DEFAULT_REQUEST_ID = 0
data class BaseAction(var action: Int) {

    var mRequestId: Int = DEFAULT_REQUEST_ID
    var mSuccess: Boolean = true
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