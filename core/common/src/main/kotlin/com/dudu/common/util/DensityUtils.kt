package com.dudu.common.util

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

/**
 * 像素获取/转换
 * Created by Dzc on 2023/5/14.
 */
object DensityUtils {
    /**
     * dp转px
     */
    val Int.dp
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    val Int.sp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()

    //    @JvmStatic
//    fun dp2px(context: Context, dpValue: Int): Int {
//        val scale = Resources.getSystem().displayMetrics.density
//        return (dpValue * scale + 0.5f).toInt()
//    }

    /**
     * 屏幕高度像素
     */
    fun getScreenHeightPixels() = Resources.getSystem().displayMetrics.heightPixels

    /**
     * 屏幕宽度px
     */
    fun getScreenWidthPixels() = Resources.getSystem().displayMetrics.widthPixels


}