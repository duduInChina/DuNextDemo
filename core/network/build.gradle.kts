plugins {
    alias(libs.plugins.dudu.library)
}

android {
    namespace = "com.dudu.network"

    defaultConfig {
        // 启动http执行日志
        buildConfigField("Boolean", "NETWORK_DEBUG", "false")
        // 开启okhttp profile插件，需开启debug
        buildConfigField("Boolean", "NETWORK_PROFILE", "true")
    }

    // 启用 buildConfigField
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.common)

    api(libs.retrofit.core)
    api(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.localebro.okhttp.profiler)
}
