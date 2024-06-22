package com.dudu.common.ext

import android.database.Cursor
import com.dudu.common.log.LogManager

/**
 * author : duzicong
 * time   : 2024/06/22
 * desc   : Log扩展方法
 */

fun String.logD(tag: String? = null) = LogManager.logLoader.d(tag, this)

fun String.logE(tag: String? = null) = LogManager.logLoader.e(tag, null,this)

fun String.logW(tag: String? = null) = LogManager.logLoader.w(tag, this)

fun String.logV(tag: String? = null) = LogManager.logLoader.v(tag, this)

fun String.logI(tag: String? = null) = LogManager.logLoader.i(tag, this)

fun String.logX(tag: String? = null) = LogManager.logLoader.x(tag, this)

fun <T> List<T>.log(tag: String? = null) = LogManager.logLoader.obj(tag, this)

fun Cursor.log(tag: String? = null) = LogManager.logLoader.obj(tag, this)

fun Any.log(tag: String? = null) = LogManager.logLoader.obj(tag, this)