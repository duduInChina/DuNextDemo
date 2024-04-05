/**
 * AndroidX Support Google 官方包
 * Android KTX 扩展库，https://developer.android.google.cn/kotlin/ktx?hl=zh-cn
 */
object VersionAndroidX {
    // core包+ktx扩展函数
    const val coreKtx = "androidx.core:core-ktx:1.12.0"

    //appcompat中默认引入了很多库，比如activity库、fragment库、core库、annotation库、drawerLayout库、appcompat-resources等
    const val appcompat = "androidx.appcompat:appcompat:1.6.1"

    // 材料设计
    const val material = "com.google.android.material:material:1.11.0"

    // 下拉刷新
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    /**
     * Android 12 提供了启动画面的API, 该库为向后兼容库，参考：https://developer.android.google.cn/guide/topics/ui/splash-screen/migrate?hl=zh-cn
     */
    const val coreSplashscreen = "androidx.core:core-splashscreen:1.0.1"

    /**
     * app启动时通过ContentProvider，
     * 初始化其他业务，优化启动流程 https://juejin.cn/post/7060349714205999140
     */
    const val startupRuntime = "androidx.startup:startup-runtime:1.1.1"

    /**
     * fragment+ktx扩展函数
     * 可以使用 lambda 来简化 Fragment 事务,viewModels 和 activityViewModels 属性委托在一行中绑定到 ViewModel
     */
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.6.2"

    private const val lifecycleVersion = "2.5.1"

    /**
     * 扩展 viewModelScope 功能，用于在后台线程中发出网络请求。该库会处理所有设置和相应的范围清除
     */
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycleVersion}"

    /**
     * lifecycle编译处理器
     */
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${lifecycleVersion}"

    /**
     * livedata，asLivedata() 方法,Flow 转换为livedata
     */
    const val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${lifecycleVersion}"

}