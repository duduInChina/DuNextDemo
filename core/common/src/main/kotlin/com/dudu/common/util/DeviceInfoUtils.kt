package com.dudu.common.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import java.util.Locale

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/08/07
 *     desc   : 设备信息
 * </pre>
 */
object DeviceInfoUtils {

    fun getDeviceIMEI(context: Context): String {
        var imei = ""
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT < 29) {
                val method = tm.javaClass.getMethod("getImei")
                imei = method.invoke(tm) as String
            } else {
                imei =
                    Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                        .uppercase(
                            Locale.getDefault()
                        )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return imei
    }

}