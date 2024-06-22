import com.dudu.ModuleData

/**
 * 项目配置
 */
object ProjectConfig{
    // 指定android 14 sdk编译
    const val compileSdk = 34

    // 最低兼容8.0
    const val minSdk = 26

    // 表示已经在Android 14 中已经做过充分测试
    const val targetSdk = 34

    // 版本号
    const val versionCode = 1

    // 版本名
    const val versionName = "0.0.1"

    const val applicationId = "com.dudu.demo"

    // debug包,包名后缀
    const val debugApplicationIdSuffix = ".test"

    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    // 下面是测试签名信息，正式签名由Github Secrets保管
    // 密钥文件名
    const val storeFile = "debug-keystore.jks"

    // 密钥密码
    const val storePassword = "android"

    // 密钥别名
    const val keyAlias = "androiddebugkey"

    // 别名密码
    const val keyPassword = "android"

    // mmap缓存目录名称
    const val xLogCacheDirName = "xlog-cache"

    // 实际保存目录
    const val xLogDirName = "xlog"

    // 配置某个模块以Application，由于hilt必须Application关系当前以module-run模块执行
    private val moduleToApplication = mapOf(
        "Weather" to false
    )

    fun isModule(moduleName: String): Boolean {
        moduleToApplication[moduleName]?.let {
            if(it) return false
        }
        return true
    }

    // 运行的模块, 适配moduleInfo获取信息
    const val moduleRun = "Log"

    val moduleInfo = mapOf(
        "Weather" to ModuleData("Weather", "天气预报", "http://dudu.com/weather"),
        "ObjectCache" to ModuleData("ObjectCache", "对象缓存", "http://dudu.com/object/cache"),
        "ViewModelTest" to ModuleData("ViewModelTest", "ViewModel测验", "http://dudu.com/viewmodel/test"),
        "Log" to ModuleData("Log", "Log", "http://dudu.com/log"),
    )

}