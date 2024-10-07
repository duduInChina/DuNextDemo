package com.dudu.common.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader


/**
 * <pre>
 *     author : dzc
 *     time   : 2024/09/25
 *     desc   : 执行命令
 * </pre>
 */
object SellUtils {
    private val LINE_SEP: String = System.getProperty("line.separator")

    /**
     * 是否有root权限
     */
    fun isAppRoot() = execCmd(listOf("echo root"), isRoot = true)

    /**
     * 静默安装apk
     */
    fun silentInstall(apkPath: String, isRoot: Boolean) = execCmd(
        listOf("pm install -r '${apkPath}'"),
        isRoot = isRoot
    )

    fun execCmd(
        commands: List<String> = listOf(),
        envp: Array<String>? = null,
        isRoot: Boolean,
        isNeedResultMsg: Boolean = true
    ) = flow {
        var result = -1
        val successMsg = StringBuilder()
        val errorMsg = StringBuilder()
        if (commands.isNotEmpty()) {
            val process = Runtime.getRuntime().exec(if (isRoot) "su" else "sh", envp, null)
            val os = DataOutputStream(process.outputStream)
            for (command in commands) {
                os.write(command.toByteArray())
                os.writeBytes(LINE_SEP)
                os.flush()
            }
            os.writeBytes("exit$LINE_SEP")
            os.flush()
            result = process.waitFor()
            if (isNeedResultMsg) {
                val successResult = BufferedReader(
                    InputStreamReader(process.inputStream, "UTF-8")
                )
                val errorResult = BufferedReader(
                    InputStreamReader(process.errorStream, "UTF-8")
                )
                var line: String?
                if ((successResult.readLine().also { line = it }) != null) {
                    successMsg.append(line)
                    while ((successResult.readLine().also { line = it }) != null) {
                        successMsg.append(LINE_SEP).append(line)
                    }
                }
                if ((errorResult.readLine().also { line = it }) != null) {
                    errorMsg.append(line)
                    while ((errorResult.readLine().also { line = it }) != null) {
                        errorMsg.append(LINE_SEP).append(line)
                    }
                }
                successResult.close()
                errorResult.close()
            }
            os.close()
            process.destroy()
        }

        emit(SellResult(result, successMsg.toString(), errorMsg.toString()))
    }.flowOn(Dispatchers.IO)

}

data class SellResult(
    val result: Int,
    val successMsg: String,
    val errorMsg: String
)