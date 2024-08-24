import org.jetbrains.kotlin.fir.util.setMultimapOf

plugins {
    alias(libs.plugins.dudu.library)
}

android {
    namespace = "com.dudu.common"
    buildFeatures {
        viewBinding = true
    }
    buildTypes {
        debug {
            resValue("string", "app_name_common", "DuNextDemoTest")
        }
    }
    defaultConfig {
        // mmap缓存目录名称
        buildConfigField("String", "XLOG_CACHE_DIR", "\"${ProjectConfig.xLogCacheDirName}\"")
         // 实际保存目录
        buildConfigField("String", "XLOG_DIR", "\"${ProjectConfig.xLogDirName}\"")

        ndk {
            // xCrash 加入配置
            abiFilters.addAll(setOf("x86", "x86_64", "armeabi-v7a", "arm64-v8a"))
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.startup)
    api(libs.android.material)
    api(libs.logger)
    api(libs.xlog)
    api(libs.xcrash)
}
