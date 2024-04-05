package com.dudu.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.util.jar.JarFile

abstract class AnnotationTask : DefaultTask(){

    //所有的jar文件输入信息
    @get:InputFiles
    abstract val allJars: ListProperty<RegularFile>

    //所有的class文件输入信息
    @get:InputFiles
    abstract val allDirectories: ListProperty<Directory>

    //经过插桩修改后的输出信息
    @get:OutputFile
    abstract val output: RegularFileProperty

    @TaskAction
    fun taskAction() {
        println("taskAction:" + allDirectories.get().size)

        println("allJars:" + allJars.get().size)

        // 遍历class
        allDirectories.get().forEach{ directory ->
            directory.asFile.walk().forEach { file ->
                if(file.isFile){
                    println("file:" + file.name)
                    if (file.absolutePath.endsWith(".class")) {
                        val inputStream = file.inputStream()
                        val classReader = ClassReader(inputStream)
                        val classNode = ClassNode()
                        classReader.accept(classNode, ClassReader.EXPAND_FRAMES)
                        // 获取注解
                        val annotation = classNode.invisibleAnnotations
                        if(annotation != null && annotation.isNotEmpty()){
                            println("annotation:" + annotation.toString())
                            annotation.forEach { aNode ->
                                println("aNode:" + aNode.values.toString())
                            }
                        }
                        inputStream.close()
                    }
                }
            }
        }

        allJars.get().forEach { jarInputFile ->
            val jarFile = JarFile(jarInputFile.asFile)
            jarFile.entries().iterator().forEach { jarEntry ->
                //过滤掉非class文件，并去除重复无效的META-INF文件
                if (jarEntry.name.endsWith(".class") && !jarEntry.name.contains("META-INF")) {
                    println("jarEntry:" + jarEntry.name)
                }
            }
        }

    }

}