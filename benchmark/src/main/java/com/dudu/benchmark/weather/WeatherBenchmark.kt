package com.dudu.benchmark.weather

import android.content.Intent
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import com.dudu.benchmark.PACKAGE_NAME
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun selectPlace() = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),// 捕获基准测试所生成帧（如滚动或动画）的时间信息
        iterations = 1,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
        }
    ) {
        startActivityAndWait(
            Intent("com.dudu.WEARTHER")
        )

        device.findObject(By.res(packageName, "searchPlaceEdit")).text = "佛山南海"

        // 找到RV
        val rv = device.findObject(By.res(packageName, "recyclerView"))
        // 查看RV中的元素>=1
        rv.wait({
            it.childCount >= 1
        },3_000)

        // 点击第一个元素
        rv.children[0].click()

    }

}