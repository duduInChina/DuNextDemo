plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.0-1.0.13")
    implementation(project(":core:annotation"))

}