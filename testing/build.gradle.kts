plugins {
    alias(libs.plugins.dudu.library)
}

android {
    namespace = "com.dudu.testing"
}

dependencies {
    api(projects.data.weatherData)
}