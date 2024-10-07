package com.dudu.download

import com.dudu.download.data.DownloadRepository
import com.dudu.download.data.DownloadTask
import com.dudu.download.data.model.DownloadStatus
import com.dudu.download.data.model.DownloadTaskData
import com.dudu.network.request.IRequest
import javax.inject.Inject

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/09/24
 *     desc   : 下载管理器
 * </pre>
 */
class DownloadManager @Inject constructor(
    private val downloadRepository: DownloadRepository
) {

    companion object {
        fun downloadProgress(status: DownloadStatus.Downloading) =
            if (status.readLength > 0)
                (status.readLength * 100 / status.countLength).toInt()
            else 0
    }

    fun buildApkTask(url: String, requestBody: IRequest? = null): DownloadTask {
        val task = DownloadTask(
            DownloadTaskData(
                url,
                method = "POST",
                requestBody,
                "${System.currentTimeMillis()}.apk"
            ),
            downloadRepository
        )
        return task
    }

    fun buildTask(taskData: DownloadTaskData): DownloadTask {
        val task = DownloadTask(taskData, downloadRepository)
        return task
    }

}