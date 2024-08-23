plugins {
    alias(libs.plugins.dudu.library)
}

android {
    namespace = "com.dudu.database"
    defaultConfig {
        // mmap缓存目录名称
        buildConfigField("String", "DATABASE_NAME", "\"${ProjectConfig.databaseName}\"")
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(projects.core.common)
    implementation(libs.room)
    ksp(libs.room.compiler)
}