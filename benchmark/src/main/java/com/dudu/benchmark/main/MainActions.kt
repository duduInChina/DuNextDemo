package com.dudu.benchmark.main

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice

/**
 * 等待主页面数据加载出来
 */
fun MacrobenchmarkScope.mainWaitForContent(){
    // 找到RV
    val obj = device.findObject(By.res(packageName, "recyclerView"))
    // 查看RV中的元素>=1
    obj.wait({
        it.childCount >= 1
    },3_000)
}