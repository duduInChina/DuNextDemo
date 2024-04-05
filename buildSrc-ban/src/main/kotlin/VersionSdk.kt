/**
 * 第三方sdk
 */

object Hilt {
    const val version = "2.51"

    const val hiltAndroid = "com.google.dagger:hilt-android:$version"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$version"
    const val hiltPlugin = "com.google.dagger.hilt.android"
}

object TheRouter {

    const val version = "1.2.2-rc9"

    const val router = "cn.therouter:router:$version"
    const val apt = "cn.therouter:apt:$version"
    const val theRouterPlugin = "cn.therouter.agp8"

}

/**
 * json解析
 */
object Json {
    // 解析效率高，https://blog.jetbrains.com/zh-hans/kotlin/2021/05/kotlinx-serialization-1-2-released/
    const val kotlinxJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0"
}

/**
 * ksp插件
 * kotlin 从1.9.0升级到1.9.23原因，使用hilt完成adapter过程中，adapter涉及到View binding的泛型，由于View binding由KAPT完成处理，而KSP处理hilt无法获取到生成的类
 * 而KSP-1.9.23版本修复了上述问题，导致kotlin需要一致的版本升级到1.9.23
 */
object Ksp {
    const val version = "1.9.23-1.0.19"
    const val kspPlugin = "com.google.devtools.ksp"
}

/**
 * proto文件处理插件
 */
object Protobuf {
    const val version = "0.9.4"
    const val protobufPlugin = "com.google.protobuf"
    const val protobufKotlinLite = "com.google.protobuf:protobuf-kotlin-lite:3.24.4"
    const val artifact = "com.google.protobuf:protoc:3.24.4"
}

object DataStore{
    const val datastore = "androidx.datastore:datastore:1.0.0"
}

/**
 * Kotlin序列化插件
 */
object KotlinSerialization{
    const val version = "1.9.23"
    const val kotlinSerializationPlugin = "org.jetbrains.kotlin.plugin.serialization"
}

/**
 * 网络
 */
object Http{
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    // okhttp4 已经由kotlin开发协程，当前引入该版本
    const val okhttp = "com.squareup.okhttp3:okhttp:5.0.0-alpha.11"
    // okhttp日志
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11"
    // okhttp profiler请求插件，可以通过idea查看请求状态
    const val okhttpProfiler = "com.localebro:okhttpprofiler:1.0.8"
}
