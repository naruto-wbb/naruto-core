package com.naruto.core.net

import com.naruto.core.base.bean.BaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

open class BaseRepository {

    /**
     * 执行网络请求 并捕获网络异常
     */
    suspend fun <T : Any> executeResponse(
        call: suspend () -> BaseResponse<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): BaseResponse<T> {
        val response: BaseResponse<T> = try {
            call()
        } catch (e: Exception) {
            ExceptionHandle.handleException(e)
        }

        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                ExceptionHandle.checkError(response.errorCode, response.errorMsg)
            } else {
                successBlock?.let { it() }
                response
            }
        }
    }
}