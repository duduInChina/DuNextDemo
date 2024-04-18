import com.android.build.api.dsl.ApplicationExtension
import com.dudu.androidTestImplementation
import com.dudu.implementation
import com.dudu.ksp
import com.dudu.libs
import com.dudu.testImplementation
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
                apply("kotlinx-serialization")
                apply("therouter")
            }

            extensions.configure<ApplicationExtension> {
                compileSdk = ProjectConfig.compileSdk

                defaultConfig {
                    applicationId = ProjectConfig.applicationId
                    minSdk = ProjectConfig.minSdk
                    targetSdk = ProjectConfig.targetSdk
                    versionCode = ProjectConfig.versionCode
                    versionName = ProjectConfig.versionName
                    testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
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

                buildFeatures{
                    viewBinding = true
                }
            }

            dependencies {
                implementation(libs.findLibrary("androidx.appcompat").get())
                implementation(libs.findLibrary("androidx.core.ktx").get())
                implementation(libs.findLibrary("androidx.lifecycle.viewmodel.ktx").get())
                implementation(libs.findLibrary("androidx.fragment.ktx").get())
                implementation(libs.findLibrary("androidx.lifecycle.livedata.ktx").get())

                testImplementation(libs.findLibrary("junit").get())
                androidTestImplementation(libs.findLibrary("androidx.test.ext").get())
                androidTestImplementation(libs.findLibrary("androidx.test.espresso.core").get())

                implementation(libs.findLibrary("hilt.android").get())
                ksp(libs.findLibrary("hilt.compiler").get())

                implementation(libs.findLibrary("therouter").get())
                ksp(libs.findLibrary("therouter.apt").get())

                implementation(libs.findLibrary("kotlinx.serialization.json").get())
            }
        }
    }

}