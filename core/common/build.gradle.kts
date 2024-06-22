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
