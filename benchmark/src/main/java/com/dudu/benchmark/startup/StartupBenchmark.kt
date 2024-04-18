package com.dudu.benchmark.startup

import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dudu.benchmark.PACKAGE_NAME
import com.dudu.benchmark.main.mainWaitForContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 设置app build variants 为 benchmark
 * add `<profileable android:shell="true" />` to your app's manifest
 */
@RunWith(AndroidJUnit4::class)
class StartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        setupBlock = {
            // 每次测试之前执行，确保一致，通常：回到home，获取权限
            pressHome()
        }
    ) {
        startActivityAndWait()
        mainWaitForContent()
    }
}