package com.naruto.core.net

import com.naruto.core.base.bean.BaseResponse
import com.naruto.core.utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

object HttpUtil {

    /**
     * 执行网络请求 并捕获网络异常
     */
    suspend fun <T : Any> executeResponse(call: suspend () -> BaseResponse<T>): BaseResponse<T> {
        return try {
            //请求成功 包含服务端自定义异常
            call()
        } catch (e: Exception) {
            //HTTP异常
            ExceptionHandle.handleException(e)
        }
    }
}