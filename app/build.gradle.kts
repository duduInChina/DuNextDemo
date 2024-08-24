plugins {
    alias(libs.plugins.dudu.application)
    alias(libs.plugins.android.application)
    // 基准配置文件加入插件
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.dudu.app"
    // BRV 要求:多Module组件化项目要求主Module及使用DataBinding的Module都开启DataBinding及Kapt, 否则抛出
    buildFeatures.dataBinding = true

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            // 启用资源收缩，该操作由
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        // 基准配置文件加入
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
            // 用于StartupBenchmarks的执行，Ensure Baseline Profile is fresh for release builds.
            baselineProfile.automaticGenerationDuringBuild = true
        }
    }
}
dependencies {
    implementation(libs.androidx.core.splashscreen)
    implementation(projects.core.common)
    implementation(projects.weather)
    implementation(libs.androidx.profileinstaller)
    // 基准配置文件加入
    baselineProfile(projects.baselineprofile)
    implementation(libs.kotlinx.coroutines.guava)
    implementation(projects.usbSerial)
}
