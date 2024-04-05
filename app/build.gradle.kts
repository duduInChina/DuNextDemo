plugins {
    alias(libs.plugins.dudu.application)
}

android {
    namespace = "com.dudu.app"
    // BRV 要求:多Module组件化项目要求主Module及使用DataBinding的Module都开启DataBinding及Kapt, 否则抛出
    buildFeatures.dataBinding = true
}

dependencies {
    implementation(libs.androidx.core.splashscreen)
    implementation(projects.core.common)
    implementation(projects.weather)
}
