package com.naruto.core.base.bean

/**
 * 通用实体类
 */
data class BaseResponse<T : Any>(var errorCode: Int, var errorMsg: String) {

    var data: T? = null

    fun success(): Boolean {
        return errorCode == 0
    }
}