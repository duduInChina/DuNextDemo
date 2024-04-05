import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.dudu.androidTestImplementation
import com.dudu.libs
import com.dudu.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * 判断模块是否以Application配置执行
 * 加入test相关东西
 */
class ModuleToApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        if (ProjectConfig.isModule(target.name)) {
            // library
            target.pluginManager.apply("dudu.library")
            with(target){
                extensions.configure<LibraryExtension> {
                    defaultConfig {
                        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    }
                    // 指向library AndroidManifest
                    sourceSets.getByName("main") {
                        manifest.srcFile("src/main/library/AndroidManifest.xml")
                    }
                    buildFeatures {
                        viewBinding = true
                    }
                }
            }
        } else {
            // application
            target.pluginManager.apply("dudu.application")
            with(target){
                extensions.configure<ApplicationExtension> {
                    defaultConfig {
                        applicationId = ProjectConfig.applicationId + ".${target.name}"
                        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    }
                    // 指向library AndroidManifest
                    sourceSets.getByName("main") {
                        manifest.srcFile("src/main/release/AndroidManifest.xml")
                    }
                    buildFeatures {
                        viewBinding = true
                    }
                }
            }
        }

        with(target){
            dependencies {
                testImplementation(libs.findLibrary("junit").get())
                androidTestImplementation(libs.findLibrary("androidx.test.ext").get())
                androidTestImplementation(libs.findLibrary("androidx.test.espresso.core").get())
            }
        }
    }

}