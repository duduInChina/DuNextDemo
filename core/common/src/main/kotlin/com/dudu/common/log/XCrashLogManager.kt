package com.dudu.common.log

import com.dudu.common.ext.logD
import com.dudu.common.util.applicationContext
import xcrash.ICrashCallback
import xcrash.TombstoneManager
import xcrash.TombstoneParser
import xcrash.XCrash

/**
 * author : duzicong
 * time   : 2024/06/22
 * desc   : XCrash，异常日志采集，捕获java崩溃，native崩溃，ANR
 */
object XCrashLogManager {

    /**
     * 初始化XCrash
     */
    fun initXCrash(){
        val callback = ICrashCallback { logPath, emergency ->
            // 回调
            "xCrash回调 $logPath $emergency".logD()
            emergency?.let {
                // 内存紧急，立即发送提交
                logPath?.let {
                    sendThenDeleteCrashLog(it)
                }
            } ?: run {
                // 保存到文件,下次打开APP时提交
                TombstoneManager.appendSection(logPath, "expanded_key_1", "expanded_content")
            }
        }

        XCrash.init(
            applicationContext,
            XCrash.InitParameters()
                //    .setAppVersion("") // 默认获取versionName
                //    .setLogDir()       // 默认 context.getFilesDir() /tombstones
                //    .setLogFileMaintainDelayMs() // 执行文件写入前延时，默认5000ms
                //    java
                //    .setJavaRethrow(true) // 是否向系统重新抛出java异常，默认true
                //    .setJavaLogCountMax(10) // java异常log行数，默认10行
                //    .setJavaDumpNetworkInfo(true) // java异常是否存储网络信息，默认true
                //    .setJavaDumpAllThreads(true)  // java异常是否线程信息，默认true
                //    .setJavaDumpAllThreadsWhiteList(null) // java异常时需要存储的线程信息，默认为空不过滤全部存储，new String[]{"^main$", "^Binder:.*", ".*Finalizer.*"}
                .setJavaCallback(callback)  // java异常发生后回调
                //    Native
                .setNativeCallback(callback)
                //  ANR
                //    .setAnrCheckProcessState(true) // 在某些 Android 电视盒设备上，ANR 不会通过进程错误状态反映出来。在这种情况下，请将此选项设置为 false。
                .setAnrCallback(callback)
        )
    }

    /**
     * 发送上传提交log，获取日志信息，提交后删除路径
     */
    fun sendThenDeleteCrashLog(logPath: String) {
        val parse = TombstoneParser.parse(logPath)

        "上传的crash log：$parse".logD()

        TombstoneManager.deleteTombstone(logPath)
    }

    /**
     * 提交全部异常日志
     */
    fun sendAllTombstones() {
        for (item in TombstoneManager.getAllTombstones()) {
            sendThenDeleteCrashLog(item.absolutePath)
        }
    }

    /**
     * 触发java异常（测试方法）
     */
    fun testJavaCrash(runInNewThread: Boolean) = XCrash.testJavaCrash(runInNewThread)

    /**
     * 触发Native异常（测试方法）
     */
    fun testNativeCrash(runInNewThread: Boolean) = XCrash.testNativeCrash(runInNewThread)

    /**
     * 触发ANR异常（测试方法）
     */
    fun testAnrCrash() {
        while (true) {
            Thread.sleep(1000)
        }
    }
}