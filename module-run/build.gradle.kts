plugins {
    alias(libs.plugins.dudu.module.run)
}

android {
    namespace = "com.dudu.module_run"

    // 启用 buildConfigField
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.common)
}