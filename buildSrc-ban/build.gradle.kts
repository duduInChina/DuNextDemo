//plugins {
//    `kotlin-dsl`
//}
//
//repositories {
//    google()
//    mavenCentral()
//}
//
//dependencies {
//    implementation("com.android.tools.build:gradle:8.1.3")
//    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.23")
//}
//
//val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
//compileKotlin.kotlinOptions {
//    jvmTarget = "17"
//}
//
//// 这段代码的作用是强制 Gradle 使用指定版本的依赖项，以确保项目构建时不会出现依赖冲突的情况，从而提高构建稳定性和可靠性。
//configurations.all {
//    resolutionStrategy.force("androidx.transition:transition:1.4.1")
//    resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib:1.9.23")
//    resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.23")
//    resolutionStrategy.force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.23")
//    resolutionStrategy.force("org.jetbrains.kotlin:kotlin-reflect:1.9.23")
//    resolutionStrategy.force("org.jetbrains:annotations:23.0.0")
//    resolutionStrategy.force("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.1")
//    resolutionStrategy.force("com.google.code.gson:gson:2.10.1")
//    resolutionStrategy.force("com.google.dagger:dagger:2.45")
//    resolutionStrategy.force("com.squareup:javapoet:1.13.0")
//    resolutionStrategy.force("com.squareup:javawriter:2.5.0")
//}