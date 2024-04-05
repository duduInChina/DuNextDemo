import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * 通过扩展函数的方式导入功能模块
 */

/**
 * androidx各种兼容包，基本都会导入
 */
fun DependencyHandler.appcompat() {
    implementation(VersionAndroidX.coreKtx)
    implementation(VersionAndroidX.appcompat)

}

/**
 * 常用控件
 */
fun DependencyHandler.widgetLayout() {
    implementation(VersionAndroidX.material)
    implementation(VersionAndroidX.fragmentKtx)
}

/**
 * lifecycle sdk
 */
fun DependencyHandler.lifecycle() {
    // 初始项目已默认导入 2.5.1
    // lifecycle-common lifecycle-livedata-core
    // lifecycle-viewmodel lifecycle-runtime
    // lifecycle-viewmodel-savestate
    implementation(VersionAndroidX.viewModelKtx)
    implementation(VersionAndroidX.lifecycleCompiler)
    implementation(VersionAndroidX.lifecycleLivedataKtx)
}

/**
 * 测试依赖
 */
fun DependencyHandler.test() {
    testImplementation(VersionTesting.junit)
    androidTestImplementation(VersionTesting.androidJunit)
    androidTestImplementation(VersionTesting.espresso)
}

/**
 * 依赖注入
 */
fun DependencyHandler.hilt() {
    implementation(Hilt.hiltAndroid)
    ksp(Hilt.hiltCompiler)
}

/**
 * 路由
 */
fun DependencyHandler.therouter() {
    implementation(TheRouter.router)
    ksp(TheRouter.apt)
}

/**
 * json解析
 */
fun DependencyHandler.json(){
    implementation(Json.kotlinxJson)
}

/**
 * 网络
 */
fun DependencyHandler.http(){
    api(Http.retrofit)
    api(Http.converterGson)
    implementation(Http.okhttp)
    implementation(Http.loggingInterceptor)
    implementation(Http.okhttpProfiler)
}
