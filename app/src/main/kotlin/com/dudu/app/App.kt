package com.dudu.app

import android.app.Application
import com.dudu.app.util.ProfileVerifierLogger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var profileVerifierLogger: ProfileVerifierLogger

    override fun onCreate() {
        super.onCreate()

        // 加载基准配置文件
        profileVerifierLogger()
    }
}