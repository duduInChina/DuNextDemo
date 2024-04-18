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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        // 基准配置文件加入
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
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
}
