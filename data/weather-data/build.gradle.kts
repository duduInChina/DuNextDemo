plugins {
    alias(libs.plugins.dudu.library)
}

android {
    namespace = "com.dudu.weather"

    defaultConfig {
        buildConfigField("String", "WEATHER_BASEURL", "\"https://api.caiyunapp.com/\"")
        buildConfigField("String", "WEATHER_TOKEN", "\"qqvp12ifYfK1xy5P\"")
    }

    // 启用 buildConfigField
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(projects.data.datastore)
    api(projects.core.network)
}

