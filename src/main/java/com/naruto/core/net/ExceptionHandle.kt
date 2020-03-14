package com.naruto.core.net

import android.net.ParseException
import com.google.gson.JsonParseException
import com.naruto.core.R
import com.naruto.core.base.app
import com.naruto.core.base.bean.BaseResponse
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * HTTP异常处理
 */
object ExceptionHandle {

    const val UNAUTHORIZED = 401
    private const val FORBIDDEN = 403
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val BAD_GATEWAY = 502
    private const val SERVICE_UNAVAILABLE = 503
    private const val GATEWAY_TIMEOUT = 504

    /**
     * HTTP请求异常或其他网络请求发起前和请求返回后未知异常
     */
    fun <T : Any> handleException(e: Throwable): BaseResponse<T> {
        return when (e) {
            is HttpException -> {
                when (e.code()) {
                    UNAUTHORIZED, FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE ->
                        getCodeError(
                            ERROR.HTTP_ERROR,
                            app.getString(R.string.error_system_busy)
                        )
                    else -> getCodeError(
                        ERROR.HTTP_ERROR,
                        app.getString(R.string.error_system_busy)
                    )
                }
            }
            is JsonParseException, is JSONException, is ParseException -> {
                getCodeError(
                    ERROR.PARSE_ERROR,
                    app.getString(R.string.error_system_busy)
                )
            }
            is ConnectException -> {
                getCodeError(
                    ERROR.NETWORK_ERROR,
                    app.getString(R.string.error_system_busy)
                )
            }
            is SSLHandshakeException -> {
                getCodeError(
                    ERROR.SSL_ERROR,
                    app.getString(R.string.error_system_busy)
                )
            }
            is ConnectTimeoutException -> {
                getCodeError(ERROR.TIMEOUT_ERROR, app.getString(R.string.error_connection_timeout))
            }
            is SocketTimeoutException -> {
                getCodeError(ERROR.TIMEOUT_ERROR, app.getString(R.string.error_connection_timeout))
            }
            is UnknownHostException -> {
                getCodeError(ERROR.UNKNOWN_HOST, app.getString(R.string.error_network_disable))
            }
            else -> {
                getCodeError(ERROR.UNKNOWN, app.getString(R.string.error_system_busy))
            }
        }
    }

    private fun <T : Any> getCodeError(code: Int, errorMsg: String): BaseResponse<T> {
        return BaseResponse(code, errorMsg)
    }

    object ERROR {
        /**
         * 通用约定异常
         */
        const val SHOW_TOAST_TO_MSG = 1812
        /**
         * 未知错误 服务器端BUG，需排查
         */
        const val UNKNOWN = -1
        /**
         * 未登录或登录超时
         */
        const val VERIFY = 1001
        /**
         * JSON解析错误
         */
        const val PARSE_ERROR = 1992
        /**
         * 网络错误
         */
        const val NETWORK_ERROR = 1993
        /**
         * 协议出错
         */
        const val HTTP_ERROR = 1994
        /**
         * 证书出错
         */
        const val SSL_ERROR = 1995
        /**
         * 连接超时
         */
        const val TIMEOUT_ERROR = 1996
        /**
         * 未知host异常（网络无法连接
         */
        const val UNKNOWN_HOST = 1999
    }
}