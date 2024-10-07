package com.dudu.common.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/08/27
 *     desc   :
 * </pre>
 */
object PackageUtil {

    fun getAppVersionName(): String {
        applicationContext?.let {
            return try {
                it.packageManager.getPackageInfo(it.packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                ""
            }
        }
        return ""
    }

    fun getAppVersionCode(): Long {
        applicationContext?.let {
            return try {
                val packageInfo = it.packageManager.getPackageInfo(it.packageName, 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageInfo.longVersionCode
                } else {
                    @Suppress("DEPRECATION")
                    packageInfo.versionCode.toLong()
                }
            } catch (e: PackageManager.NameNotFoundException) {
                1
            }
        }
        return 1
    }

    /**
     * 重启app
     */
    fun restartAPP(context: Context, delayTime: Long = 2000){
        CoroutineScope(Dispatchers.IO).launch {
            delay(delayTime)
            val intent = context.packageManager.getLaunchIntentForPackage(getPackageName())
            intent?.let {
                // 清除目标 Activity 之上的所有 Activity，使目标 Activity 成为栈顶；
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivity(intent)
            }
        }
    }

}