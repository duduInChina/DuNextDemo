package com.dudu.common.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

internal var applicationContext: Context? = null

fun getResString(@StringRes id: Int) = applicationContext?.getString(id)

fun getResColor(@ColorRes id: Int) = applicationContext?.getColor(id)?:0

fun showToast(text: CharSequence){
    Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
}