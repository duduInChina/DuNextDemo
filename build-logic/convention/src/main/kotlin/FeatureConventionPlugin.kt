import com.android.build.gradle.LibraryExtension
import com.dudu.androidTestImplementation
import com.dudu.libs
import com.dudu.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * 功能业务模块
 * 加入test相关
 */
class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("dudu.library")
        with(target){
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                buildFeatures {
                    viewBinding = true
                }
            }
            dependencies {
                testImplementation(libs.findLibrary("junit").get())
                androidTestImplementation(libs.findLibrary("androidx.test.ext").get())
                androidTestImplementation(libs.findLibrary("androidx.test.espresso.core").get())
                testImplementation(project(":testing"))
                testImplementation(libs.findLibrary("mockk").get())
                testImplementation(libs.findLibrary("kotlin.test").get())
                testImplementation(libs.findLibrary("kotlinx.coroutines.test").get())
            }
        }

    }

}