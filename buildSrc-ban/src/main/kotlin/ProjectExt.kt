import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.application(): BaseAppModuleExtension {
    return extensions.getByType(BaseAppModuleExtension::class.java)
}

internal fun Project.library(): LibraryExtension {
    return extensions.getByType(LibraryExtension::class.java)
}

// Application 级别 - 扩展函数来设置 KotlinOptions
internal fun BaseAppModuleExtension.kotlinOptions(action: KotlinJvmOptions.() -> Unit) {
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure(
        "kotlinOptions",
        action
    )
}

// Library 级别 - 扩展函数来设置 KotlinOptions
internal fun LibraryExtension.kotlinOptions(action: KotlinJvmOptions.() -> Unit) {
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure(
        "kotlinOptions",
        action
    )
}