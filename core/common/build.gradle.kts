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
}

dependencies {
    implementation(libs.androidx.startup)
    api(libs.android.material)
}
