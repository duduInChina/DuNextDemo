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

    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    // 密钥文件路径
    const val storeFile = ""

    // 密钥密码
    const val storePassword = ""

    // 密钥别名
    const val keyAlias = ""

    // 别名密码
    const val keyPassword = ""

    // 配置某个模块以Application
    val moduleToApplication = mapOf(
        "Weather" to true
    )

    fun isModule(moduleName: String): Boolean {
        moduleToApplication[moduleName]?.let {
            if(it) return false
        }
        return true
    }
}