package com.dudu.plugin

import com.android.build.api.artifact.ScopedArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.AnnotationProcessor
import com.android.build.api.variant.ScopedArtifacts
import com.android.build.gradle.internal.plugins.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer

class AnnotationPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        println("Hello AnnotationPlugin2")

        val androidComponents = target.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants { variant ->
            // 注册任务
            val taskProvider = target.tasks.register("${variant.name}duduAnnotationTask", AnnotationTask::class.java)
//            ScopedArtifacts.Scope.PROJECT 当前模块，可用于Library
//            ScopedArtifacts.Scope.ALL  主要用于application主项目
            variant.artifacts.forScope(ScopedArtifacts.Scope.PROJECT) // 扫描所有class
                .use(taskProvider)
                .toTransform(
                    type = ScopedArtifact.CLASSES,
                    inputJars = AnnotationTask::allJars,
                    inputDirectories = AnnotationTask::allDirectories,
                    into = AnnotationTask::output
                )

        }
    }
}