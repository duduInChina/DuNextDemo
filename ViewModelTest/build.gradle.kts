plugins {
    alias(libs.plugins.dudu.module.feature)
}


android {
    namespace = "com.dudu.viewmodeltest"
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.brv)
}