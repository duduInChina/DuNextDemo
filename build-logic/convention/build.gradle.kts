import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.dudu.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)

//    compileOnly(libs.firebase.crashlytics.gradlePlugin)
//    compileOnly(libs.firebase.performance.gradlePlugin)
//    compileOnly(libs.room.gradlePlugin)
//    implementation(libs.truth)
}

// enableStricterValidation = true：这个属性用于设置是否启用更严格的插件验证。当设置为 true 时，表示启用更严格的插件验证。
// failOnWarning = true：这个属性用于设置是否在警告时失败。当设置为 true 时，表示在出现警告时任务会失败
//tasks {
//    validatePlugins {
//        enableStricterValidation = true
//        failOnWarning = true
//    }
//}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "dudu.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "dudu.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("moduleToApplication") {
            id = "dudu.module.application"
            implementationClass = "ModuleToApplicationConventionPlugin"
        }
        register("moduleRun") {
            id = "dudu.module.run"
            implementationClass = "ModuleRunConventionPlugin"
        }
        register("moduleFeature") {
            id = "dudu.module.feature"
            implementationClass = "FeatureConventionPlugin"
        }
        register("benchmark") {
            id = "dudu.benchmark"
            implementationClass = "BenchmarkConventionPlugin"
        }
    }
}
