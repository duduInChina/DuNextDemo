package com.dudu.common.log

import com.dudu.common.BuildConfig
import com.dudu.common.util.FileUtil
import com.dudu.common.util.getCommonDebug
import com.dudu.common.util.getResString
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog

/**
 * author : duzicong
 * time   : 2024/06/15
 * desc   : log管理类
 *          初始化
 */
object LogManager {

    /**
     * logger默认输出的Tag name
     */
    private val defaultTag by lazy {
        getResString(com.dudu.common.R.string.log_tag_name)
    }

    /**
     * XLog mmap 缓存路径
     */
    private val XLogCacheDir by lazy {
        FileUtil.getAppCachePath(BuildConfig.XLOG_CACHE_DIR)
    }

    /**
     * XLog实际保存目录
     */
    private val XLogDir by lazy {
        FileUtil.getAppCachePath(BuildConfig.XLOG_DIR)
    }

    /**
     * XLog文件结尾增加字段
     */
    private const val XLOG_FIX = "log"

    /**
     * log sdk初始化
     */
    fun initLog(){
        initLogger()
        initXLog()
    }

    /**
     * logger初始化
     */
    private fun initLogger(){
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            //    .showThreadInfo(true) // 是否显示进程，默认true
            .methodCount(2) // 显示多少行执行方法栈，默认2行
            .methodOffset(2) // 隐藏内部方法调用（方法从下往上隐藏），默认0
            //    .logStrategy(customLog) // 日志输出策略， 默认 LogCat
            .tag(defaultTag) // 日志标签. Default PRETTY_LOGGER
            .build()
        // 根据BuildConfig.DEBUG，才显示日志到控制台
        Logger.addLogAdapter(LoggerAdapter(formatStrategy))
    }

    /**
     * XLog初始化
     */
    private fun initXLog(){
        // 引入mmap Native包
        System.loadLibrary("c++_shared")
        System.loadLibrary("marsxlog")

        val xLog = Xlog()
        Log.setLogImp(xLog)
        Log.setConsoleLogOpen(false)// 暂不使用xLog输出日志
        Log.appenderOpen(
            if(getCommonDebug()) Xlog.LEVEL_DEBUG else Xlog.LEVEL_INFO,
            Xlog.AppednerModeAsync, // 异步写入文件
            XLogCacheDir,// mmap缓存路径
            XLogDir,// 实际保存目录
            XLOG_FIX,
            0// 缓存保留时间
        )

    }

}

/**
 * 控制logger debug才输出
 */
class LoggerAdapter(formatStrategy: FormatStrategy): AndroidLogAdapter(formatStrategy) {
    override fun isLoggable(priority: Int, tag: String?): Boolean {
        return getCommonDebug()
    }
}
