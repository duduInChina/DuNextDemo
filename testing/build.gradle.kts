plugins {
    alias(libs.plugins.dudu.library)
}

android {
    namespace = "com.dudu.testing"
}

dependencies {
    implementation(projects.data.weatherData)
}