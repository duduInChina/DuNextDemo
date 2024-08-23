package com.dudu.common.util

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/08/09
 *     desc   : 字符串正则验证
 * </pre>
 */
object StringRegexValid {

    fun url(url: String): Boolean{
        val regex = Regex(
            "^(https?|ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$"
        )
        return url.matches(regex)
    }

}