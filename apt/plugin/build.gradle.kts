plugins {
    id("java-library")
    id("java-gradle-plugin") //会自动引入java-library、gradleApi()
    id("org.jetbrains.kotlin.jvm") //支持kotlin编写插件
    id("maven-publish") //发布到maven
}

//java {
//    sourceCompatibility = JavaVersion.VERSION_1_7
//    targetCompatibility = JavaVersion.VERSION_1_7
//}

gradlePlugin {
    plugins {
        create("annotationPlugin") {
            group = "com.dudu.plugin"
            version = "0.0.11"
            id = "com.dudu.plugin.annotation"
            implementationClass = "com.dudu.plugin.AnnotationPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri("../../custom_plugin_repo") // 生成到本地
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:8.1.2")
    implementation("org.ow2.asm:asm:9.2")
    implementation("org.ow2.asm:asm-commons:9.2")
}