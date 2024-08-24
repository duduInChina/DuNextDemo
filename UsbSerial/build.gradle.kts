plugins {
    alias(libs.plugins.dudu.module.feature)
}

android {
    namespace = "com.dudu.usbserial"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.common)
}