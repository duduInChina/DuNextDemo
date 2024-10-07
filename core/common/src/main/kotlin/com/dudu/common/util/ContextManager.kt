package com.dudu.common.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.dudu.common.BuildConfig

internal var applicationContext: Context? = null

fun getResString(@StringRes id: Int) = applicationContext?.getString(id) ?: ""

fun getResColor(@ColorRes id: Int) = applicationContext?.getColor(id) ?: 0

fun showToast(text: CharSequence){
    applicationContext?.let {
        Toast.makeText(it, text, Toast.LENGTH_LONG).show()
    }
}

/**
 * 主要判断程序是否debug状态
 * 应用：Log输出判断
 */
fun getCommonDebug(): Boolean = BuildConfig.DEBUG

fun getPackageName() = applicationContext?.packageName ?: ""