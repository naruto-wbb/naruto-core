package com.naruto.core.utils

import android.app.ActivityManager
import android.app.AlarmManager
import android.app.NotificationManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.content.ClipboardManager
import android.content.Context
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.os.BatteryManager
import android.os.Build
import android.os.Vibrator
import android.telephony.TelephonyManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.naruto.core.base.app
import com.naruto.core.utils.DoubleClickLock.isBeyondClickInterval
import java.lang.Exception
import java.text.DecimalFormat

fun Double.trimZero(): String {
    return try {
        val decimalFormat = DecimalFormat("###################.###########")
        decimalFormat.format(this)
    } catch (ignore: Exception) {
        "0"
    }
}

object Ext {

    inline val connectivityManager: ConnectivityManager
        get() = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    inline val alarmManager: AlarmManager
        get() = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    inline val telephonyManager: TelephonyManager
        get() = app.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    inline val activityManager: ActivityManager
        get() = app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    inline val notificationManager: NotificationManager
        get() = app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    inline val appWidgetManager
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        get() = app.getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager

    inline val inputMethodManager: InputMethodManager
        get() = app.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    inline val clipboardManager
        get() = app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    inline val bluetoothManager: BluetoothManager
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        get() = app.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    inline val audioManager
        get() = app.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    inline val batteryManager
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        get() = app.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    inline val cameraManager
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        get() = app.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    inline val vibrator
        get() = app.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    inline val currentTimeMillis: Long
        get() = System.currentTimeMillis()

    fun ImageView.loadImg(
        context: Context? = null,
        fragment: Fragment? = null,
        imgUrl: String? = null
    ) {
        UIUtil.loadImg(context ?: fragment, this, imgUrl)
    }

    inline fun View.doubleClickLock(crossinline function: () -> Unit) {
        isBeyondClickInterval(function)
    }
}