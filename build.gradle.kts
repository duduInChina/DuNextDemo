import org.gradle.kotlin.dsl.PluginDependenciesSpecScope
// Top-level build file where you can add configuration options common to all sub-projects/modules.
// 当前kotlin版本1.9.3，ksp需版本一致（ksp 相关版本号https://github.com/google/ksp/releases?page=2）
//  使用buildSrc配置项
//    id(Ksp.kspPlugin) version Ksp.version apply false
//    id(Hilt.hiltPlugin) version Hilt.version apply false
//    id(TheRouter.theRouterPlugin) version TheRouter.version apply false
//    id(Protobuf.protobufPlugin) version Protobuf.version apply false
//    id(KotlinSerialization.kotlinSerializationPlugin) version KotlinSerialization.version apply false
//  常规配置项
//    id("com.android.application") version "8.1.3" apply false
//    id("com.android.library") version "8.1.3" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
//    id("org.jetbrains.kotlin.jvm") version "1.9.23" apply false
//    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
//    id("com.google.dagger.hilt.android") version "2.51" apply false
//    id("cn.therouter.agp8") version "1.2.2-rc9" apply false
//    id("com.google.protobuf") version "0.9.4" apply false
//    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23" apply false
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false

    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.therouter) apply false
}
