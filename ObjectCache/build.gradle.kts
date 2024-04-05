plugins {
    alias(libs.plugins.dudu.module.feature)
}


android {
    namespace = "com.dudu.objectcache"
}

dependencies {
    implementation(projects.core.common)
}