package com.dudu.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dudu.common.util.PackageUtil
import com.dudu.common.util.getPackageName

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/09/26
 *     desc   : 升级更新广播获取
 *     注册：
 *     <receiver android:name=".receiver.UpdateReceiver"
 *             android:enabled="true"
 *             android:exported="true">
 *             <intent-filter android:priority="100">
 *                 <action android:name="android.intent.action.PACKAGE_ADDED" />
 *                 <action android:name="android.intent.action.PACKAGE_REPLACED" />
 *                 <action android:name="android.intent.action.PACKAGE_REMOVED" />
 *                 <data android:scheme="package" />
 *             </intent-filter>
 *         </receiver>
 * </pre>
 */
class UpdateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val packageName = intent.dataString
            if (it.action == Intent.ACTION_PACKAGE_REPLACED) {
                // app 更新广播，应只有root权限设备才会进行广播回调
                if (packageName == "package:${getPackageName()}") {
                    // 重启app
                    context?.let { context ->
                        PackageUtil.restartAPP(context)
                    }
                }
            } else if (it.action == Intent.ACTION_PACKAGE_ADDED) {

            } else if (it.action == Intent.ACTION_PACKAGE_REMOVED) {

            }
        }
    }
}