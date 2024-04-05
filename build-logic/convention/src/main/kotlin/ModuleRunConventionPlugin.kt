import com.android.build.api.dsl.ApplicationExtension
import com.dudu.ModuleData
import com.dudu.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class ModuleRunConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        var moduleInfo = ModuleData("", "module_run", "")
        ProjectConfig.moduleInfo[ProjectConfig.moduleRun]?.let {
            moduleInfo = it
        }
        val appId = moduleInfo.moduleName.ifEmpty { "module.run" }
        with(target){
            pluginManager.apply("dudu.application")
            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = ProjectConfig.applicationId + ".${appId}"
                    resValue("string", "module_run_name", moduleInfo.appName)
                    buildConfigField("String", "ROUTER_URL", "\"${moduleInfo.routerUrl}\"")
                }

                // 启用 buildConfigField
                buildFeatures {
                    buildConfig = true
                }
            }

            dependencies {
                if(moduleInfo.moduleName.isNotEmpty()){
                    implementation(project(":${moduleInfo.moduleName}"))
                }
            }
        }
    }
}