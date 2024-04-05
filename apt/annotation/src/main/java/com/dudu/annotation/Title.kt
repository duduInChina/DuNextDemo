package com.dudu.annotation

// 当前未使用，考虑使用注解，apt+agp的方式读取，生成静态文件
/**
 * RetentionPolicy.SOURCE：表示该注解仅在源码阶段保留，编译时会被丢弃，不会包含在编译后的类文件中。
 * RetentionPolicy.CLASS：表示该注解在编译时会被保留到类文件中，但在运行时不可获取。这是默认的保留策略。
 * RetentionPolicy.RUNTIME：表示该注解在运行时保留，可以通过反射机制在运行时获取并处理。
 */

@Target(AnnotationTarget.CLASS) // 可用于注解类上面
@Retention(AnnotationRetention.SOURCE)
annotation class Title(
    val value: String = ""
)
