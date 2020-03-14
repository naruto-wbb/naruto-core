package com.naruto.core.utils

import android.os.Build
import android.os.Handler
import android.os.Message
import android.widget.Toast
import com.naruto.core.base.app
import java.lang.reflect.Field

object ToastUtil {

    private var sField_TN: Field? = null
    private var sField_TN_Handler: Field? = null


    fun showLong(textRes: Int) {
        showLong(app.getString(textRes))
    }

    fun showLong(text: String?) {
        val toast = Toast.makeText(app, text, Toast.LENGTH_LONG)
        toast.setText(text)
        // 修复7.1.1 Toast WindowManager$BadTokenException 错误
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            hookHandler(toast)
        }
        toast.show()
    }

    private fun hookHandler(toast: Toast) {
        try {
            val tn = sField_TN!![toast]
            val preHandler =
                sField_TN_Handler!![tn] as Handler
            sField_TN_Handler!![tn] = SafelyHandlerWarpper(preHandler)
        } catch (ignored: Exception) {
        }
    }

    private class SafelyHandlerWarpper(private val impl: Handler) :
        Handler() {
        override fun dispatchMessage(msg: Message) {
            try {
                super.dispatchMessage(msg)
            } catch (ignored: Exception) {
            }
        }

        override fun handleMessage(msg: Message) {
            impl.handleMessage(msg)
        }
    }

    init {
        try {
            sField_TN = Toast::class.java.getDeclaredField("mTN")
            sField_TN?.isAccessible = true
            sField_TN_Handler = sField_TN?.type?.getDeclaredField("mHandler")
            sField_TN_Handler?.isAccessible = true
        } catch (ignored: Exception) {
        }
    }
}