import com.dudu.testImplementation

plugins {
    alias(libs.plugins.dudu.module.feature)
    id("kotlin-kapt")
}


android {
    namespace = "com.dudu.weather"

    buildFeatures.dataBinding = true
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.data.weatherData)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.brv)
}