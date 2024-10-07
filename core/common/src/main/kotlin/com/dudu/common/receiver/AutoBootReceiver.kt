package com.dudu.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dudu.common.util.PackageUtil

/**
 * author : duzicong
 * time   : 2024/06/15
 * desc   : 自启动广播 - 系统启动打开app
 *          在首次启动时发送一次，并且许多应用需要接收这些广播，例如调度作业和闹钟。
 *          注册：
 *          <receiver android:name=".receiver.AutoBootReceiver"
 *             android:enabled="true"
 *             android:exported="true">
 *             <intent-filter>
 *                 <action android:name="android.intent.action.BOOT_COMPLETED" />
 *                 <category android:name="android.intent.category.HOME" />
 *             </intent-filter>
 *         </receiver>
 */
class AutoBootReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            when(intent.action){
                Intent.ACTION_BOOT_COMPLETED -> {
                    context?.let {
                        // 启动app操作
                        PackageUtil.restartAPP(it)
                    }
                }

                else -> {}
            }
        }

    }
}