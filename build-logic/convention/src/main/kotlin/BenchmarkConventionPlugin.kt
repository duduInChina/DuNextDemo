import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestExtension
import com.dudu.implementation
import com.dudu.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * 基准分析，基本插件和包配置
 */
class BenchmarkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply("org.jetbrains.kotlin.android")
                apply("androidx.baselineprofile")
            }

            extensions.configure<TestExtension> {
                compileSdk = ProjectConfig.compileSdk

                defaultConfig {
                    minSdk = ProjectConfig.minSdk
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    buildConfigField("String", "PACKAGE_NAME", "\"${ProjectConfig.applicationId}\"")
                }

                // 启用 buildConfigField
                buildFeatures {
                    buildConfig = true
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }

                tasks.withType<KotlinCompile>().configureEach {
                    kotlinOptions {
                        jvmTarget = JavaVersion.VERSION_1_8.toString()
                    }
                }
            }

            dependencies {
                implementation(libs.findLibrary("androidx.test.ext.junit").get())
                implementation(libs.findLibrary("androidx.test.espresso.core").get())
                implementation(libs.findLibrary("uiautomator").get())
                implementation(libs.findLibrary("benchmark.macro.junit4").get())
            }
        }
    }
}
