import org.gradle.api.initialization.resolve.RepositoriesMode

pluginManagement {
    // 版本管理
    includeBuild("build-logic")
    repositories {
        // 本地制作的插件
        maven { url = uri("./custom_plugin_repo") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/releases") }
        google()
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    // 被 @Incubating 标记，仍再孵化开发阶段
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/nexus/content/repositories/releases") }
        google()
        mavenCentral()
         // JCenter Maven 仓库在2021年5月1日后不再接收任何新的库版本、更新或修复补丁。
        maven { setUrl("https://jitpack.io") }

    }
}
//include(":core:annotation")
//include(":core:annotation-processors")
//include(":core:plugin")

rootProject.name = "DuNextDemo"
// 可以开启projects引用，https://docs.gradle.org/7.0/release-notes.html
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":module-run")
include(":baselineprofile")
include(":benchmark")
include(":app")
include(":testing")
include(":core:common")
include(":core:download")
include(":core:network")
include(":data:datastore-proto")
include(":data:datastore")
include(":data:weather-data")
include(":database")

include(":Weather")
include(":ObjectCache")
include(":ViewModelTest")
include(":Log")
include(":UsbSerial")
