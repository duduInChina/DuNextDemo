plugins {
    alias(libs.plugins.dudu.module.feature)
}

android {
    namespace = "com.dudu.log"
}

dependencies {
    implementation(projects.core.common)
}