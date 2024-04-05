import com.android.build.gradle.LibraryExtension
import com.dudu.implementation
import com.dudu.ksp
import com.dudu.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * 当前先做汇总型的插件，后续再根据具体需求进行拆分
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
                apply("kotlinx-serialization")
            }

            extensions.configure<LibraryExtension> {
                compileSdk = ProjectConfig.compileSdk

                defaultConfig {
                    minSdk = ProjectConfig.minSdk
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
                implementation(libs.findLibrary("androidx.appcompat").get())
                implementation(libs.findLibrary("androidx.core.ktx").get())
                implementation(libs.findLibrary("androidx.lifecycle.viewmodel.ktx").get())
                implementation(libs.findLibrary("androidx.fragment.ktx").get())
                implementation(libs.findLibrary("androidx.lifecycle.livedata.ktx").get())

                implementation(libs.findLibrary("hilt.android").get())
                ksp(libs.findLibrary("hilt.compiler").get())

                implementation(libs.findLibrary("therouter").get())
                ksp(libs.findLibrary("therouter.apt").get())

                implementation(libs.findLibrary("kotlinx.serialization.json").get())
            }

        }
    }
}