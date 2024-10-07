plugins {
    alias(libs.plugins.dudu.library)
}

android {
    namespace = "com.dudu.download"

    defaultConfig {
        // 下载文件目录名称
        buildConfigField("String", "DOWNLOAD_DIR", "\"${ProjectConfig.downloadDirName}\"")

    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.network)
}