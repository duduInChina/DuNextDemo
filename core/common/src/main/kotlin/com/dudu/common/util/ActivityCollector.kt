package com.dudu.common.util

import android.app.Activity
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/07/02
 *     desc   : 应用中所有Activity的管理器，可用于一键杀死所有Activity。
 * </pre>
 */
object ActivityCollector {

    private val activityList = ArrayList<WeakReference<Activity>?>()

    fun add(weakRefActivity: WeakReference<Activity>?) {
        activityList.add(weakRefActivity)
    }

    fun remove(weakRefActivity: WeakReference<Activity>?) {
        activityList.remove(weakRefActivity)
    }

    fun finishAll() {
        if (activityList.isNotEmpty()) {
            for (activityWeakReference in activityList) {
                val activity = activityWeakReference?.get()
                if (activity != null && !activity.isFinishing) {
                    activity.finish()
                }
            }
            activityList.clear()
        }
    }

}