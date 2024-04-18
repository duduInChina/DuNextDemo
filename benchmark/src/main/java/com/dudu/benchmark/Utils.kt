package com.dudu.benchmark

import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

val PACKAGE_NAME = buildString {
//    append(BuildConfig.PACKAGE_NAME)
//    append(BuildConfig.APP_FLAVOR_SUFFIX)
    append("com.dudu.demo")
}

/**
 * 有些ui是有数据才加载出来的等待元素出现
 */
fun UiDevice.waitAndFindObject(selector: BySelector, timeout: Long): UiObject2 {
    if (!wait(Until.hasObject(selector), timeout)) {
        throw AssertionError("Element not found on screen in ${timeout}ms (selector=$selector)")
    }

    return findObject(selector)
}