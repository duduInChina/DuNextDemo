package com.dudu.download.data.model

import com.dudu.common.util.FileUtil
import com.dudu.download.BuildConfig
import com.dudu.network.request.IRequest
import java.io.File

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/09/24
 *     desc   : 下载任务的数据，可作为数据表
 * </pre>
 */
data class DownloadTaskData(
    var url: String,
    var method: String = "GET",// 请求类型
    var requestBody: IRequest? = null,
    var fileName: String? = null,
    var localFilePath: String? = null,// 文件全路径
    var status: Int = 0, // 当前下载状态
    var ignoreLocal: Boolean = true, // 忽略本地已存在文件，默认忽略, 不忽略启用断点续传
    var autoInstall: Boolean = false, // apk自动安装，常规检查权限手动点击允许安装
    // 原文件长度，从请求文件接口读取，判断是否断点续传，不清楚文件是否完整，删除文件再下载，如：长度-1，但文件存在，无法判断文件是否完整续传（删除重新下）
    var contentLength: Long = -1L,
    // 已读取的长度
    var readLength: Long = 0L,
    // 失败信息
    var failedMessage: String? = null,
) {
    init {
        fileName ?: let {
            fileName = FileUtil.fileNameFormUrl(url)
        }
        localFilePath ?: let {
            localFilePath =
                FileUtil.getAppCachePath(BuildConfig.DOWNLOAD_DIR) + File.separator + fileName
        }
    }
}
