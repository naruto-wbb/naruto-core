package com.naruto.core.base.bean

/**
 * 通用接口返回实体类
 */
data class BaseResponse<T : Any>(var code: Int, var message: String) {

    private var result: Boolean = false
    var count: Int = 0
    var index: Int = 0
    var size: Int = 0
    var pages: Int = 0

    //TODO 返回空值
    var data: T? = null

    fun success(): Boolean {
        return result
    }
}