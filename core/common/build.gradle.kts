plugins {
    alias(libs.plugins.dudu.library)
}

android {
    namespace = "com.dudu.common"
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.startup)
    api(libs.android.material)
}
