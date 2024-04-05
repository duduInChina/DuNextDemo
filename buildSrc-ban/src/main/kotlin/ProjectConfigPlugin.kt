import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * 项目配置插件
 */
class ProjectConfigPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("--->"+project.name)
        setConfig(project)
    }

    private fun setConfig(project: Project){


//        ProjectConfig.moduleToApplication[project.name]?.let {
//            if(it) {
//                // 模块转化为Application，引导入release的AndroidManifest
////                isApplication = true
////                isLibrary = false
//                project.apply{
//                    plugin("com.android.application")
//                }
//                project.application().apply {
//                    sourceSets.getByName("main") {
//                        manifest.srcFile("src/main/release/AndroidManifest.xml")
//                    }
//                }
//            } else {
//                // 还是以Library，引导入library的AndroidManifest
////                isApplication = false
////                isLibrary = true
//                project.apply{
//                    plugin("com.android.library")
//                }
//                project.library().apply {
//                    sourceSets.getByName("main") {
//                        manifest.srcFile("src/main/library/AndroidManifest.xml")
//                    }
//                }
//            }
//        }
        var isApplication = project.plugins.hasPlugin("com.android.application")
        var isLibrary = project.plugins.hasPlugin("com.android.library")
        if(isApplication){
            // 处理 com.android.application 模块逻辑
            setConfigByApplication(project)
        } else if(isLibrary) {
            setConfigByLibrary(project)
        }
    }


    private fun setConfigByLibrary(project: Project) {
        // 添加插件
        project.apply{
            plugin("org.jetbrains.kotlin.android")
            plugin("com.google.devtools.ksp")
            plugin("com.google.dagger.hilt.android")
            plugin("kotlinx-serialization")
        }

        project.library().apply {
            compileSdk = ProjectConfig.compileSdk

            defaultConfig {
                minSdk = ProjectConfig.minSdk
                testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

            kotlinOptions {
                jvmTarget = "1.8"
            }

            buildFeatures{
                viewBinding = true
            }
        }

        project.dependencies {
            appcompat()
            widgetLayout()
            lifecycle()
            test()
            hilt()
            therouter()
            json()
        }
    }

    private fun setConfigByApplication(project: Project) {
        // 添加插件
        project.apply{
            plugin("org.jetbrains.kotlin.android")
            plugin("com.google.devtools.ksp")
            plugin("com.google.dagger.hilt.android")
            plugin("kotlinx-serialization")
            // Application 项目需加入theRouter插件构建router.json
            plugin("therouter")
        }

        project.application().apply {
            compileSdk = ProjectConfig.compileSdk

            defaultConfig {
                applicationId = ProjectConfig.applicationId
                minSdk = ProjectConfig.minSdk
                targetSdk = ProjectConfig.targetSdk
                versionCode = ProjectConfig.versionCode
                versionName = ProjectConfig.versionName
                testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

            kotlinOptions {
                jvmTarget = "1.8"
            }

            buildFeatures{
                viewBinding = true
            }
        }

        project.dependencies {
            appcompat()
            widgetLayout()
            lifecycle()
            test()
            hilt()
            therouter()
        }
    }

}
