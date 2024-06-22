package com.dudu.app

import android.content.Intent
import com.dudu.common.router.RouterPath

/**
 * 主页面数据
 * Created by Dzc on 2023/6/22.
 */
data class MainData(val title: String, val content: String,  val intent: Intent? = null, val routerPath: String ?= null)

val mainDataList by lazy {
    mutableListOf(
        MainData(
            "Common Api", """
            • Hilt：自动依赖注入
            • AppStartup：使用ContentProvider优化启动初始化流程，减轻Application负担
            • SplashScreen启动画面：优化启动白屏问题
            • BaseView：顶层赋值完成Title、ViewBinding、ViewModel初始化
            • RootLayout：Activity和Fragment加入根布局，装载标题、加载UI、空布局UI
            • PermissionX：权限判断，在当前Activity加入空布局的Fragment完成判断流程
            • build-logic：注册插件模块，ProjectConfig配置项目，libs管理包版本
            • Mockk：单元测试mock测试替身
        """.trimIndent()
        ),

        MainData(
            "天气预报", """
            根据《第一行代码》Demo进行优化调整
            • IntentViewModel：定制MVI分发流程
            • 沉浸式标题栏适配，已适配sdk30，StatusBarUtil
            • ViewModel：Base层泛型初始化实现，LiveData更改时通知视图
            • DataStore：代替SharedPreferences
            • Retrofit：网络请求，手机网络异常、服务器接口异常、接口业务异常，展示不同失败UI
                    CallAdapter：增加Flow数据流方式返回
                    Converter：增加kotlinx.serialization解析JSON，同时判断业务ErrorCode
        """.trimIndent(),
            routerPath = RouterPath.WEATHER
//            intent = Intent
        ),

        MainData("日志","""
            • 调试日志：编码环节，debug模式下，仅控制台输出，封装Logger实现
            • 数据及状态埋点日志：输出到控制台和文件归档，日志回捞（必要是推送push提交）需提交的日志文件，封装XLog实现
            • 异常日志：Crash崩溃日志，输出到控制台和文件归档，下次打开app触发提交，封装XCrash实现
            • 行为埋点日志：业务需求需记录的行为日志、曝光日志、点击日志，输出到控制台和数据库记录，根据时间段提交
            """.trimIndent(),
            routerPath = RouterPath.LOG
        ),

        MainData(
            "对象缓存", """
            防止缓存无限增长，对象重复再利用
            在对象池获取操作对象
            缓存对象优先淘汰不经常使用对象
            """.trimIndent(),
            routerPath = RouterPath.OBJECT_CACHE
        )
    )
}