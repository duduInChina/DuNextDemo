package com.dudu.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import com.dudu.benchmark.main.mainWaitForContent
import org.junit.Rule
import org.junit.Test

class MainBaselineProfile {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() = baselineProfileRule.collect("com.dudu.demo"){
        pressHome()
        startActivityAndWait()
        mainWaitForContent()
    }
}