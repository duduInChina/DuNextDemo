package com.dudu.download.data

import com.dudu.common.util.FileUtil
import com.dudu.download.data.model.DownloadStatus
import com.dudu.download.data.model.DownloadTaskData
import com.dudu.network.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import java.io.FileOutputStream
import javax.inject.Inject

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/09/24
 *     desc   :
 * </pre>
 */
class DownloadRepository @Inject constructor(

) {

    companion object {
        // 由于下载的baseurl未必是一个确定值，所以根据下载链接再次切换调整
        private const val BASEURL = "https://example.com"
    }

    private val downloadApi by lazy {
        // 下载30分钟
        RetrofitManager.createService(DownloadApi::class.java, BASEURL, 60 * 30)
    }


    fun download(data: DownloadTaskData) = flow {
        val file = FileUtil.parentFileMake(data.localFilePath!!)
        var currentLength = -1L // 当前文件长度
        if (file.exists()) {
            // 文件存在
            if (data.ignoreLocal) {
                // 忽略原文件，删除
                file.delete()
            } else {
                // 不忽略，断点续传，或者文件长度一致直接返回完成
                currentLength = file.length()
            }
        }
        if (currentLength > 0 && data.contentLength > 0 && currentLength == data.contentLength) {
            // 文件长度一致直接返回完成
            emit(DownloadStatus.Done(file))
        } else {

            val headers = mutableMapOf<String, String>()
            if (currentLength > 0) {
                // 启用断点续传
                headers["Range"] = "bytes=$currentLength-"
            }

            emit(DownloadStatus.Waiting)

            val responseBody = if(data.method == "POST")
                data.requestBody?.let {
                    downloadApi.coroutinesDownloadPost(data.url, headers, it)
                }?:let {
                    downloadApi.coroutinesDownloadPost(data.url, headers)
                }
            else
                downloadApi.coroutinesDownloadGet(data.url, headers)
            if (!isFile(responseBody)) {
                throw IllegalArgumentException("下载长度异常:" + responseBody.contentLength())
            } else {
                data.contentLength = responseBody.contentLength()
                val inputStream = responseBody.byteStream()
                val buffer = ByteArray(1024 * 8)
                var len: Int
                var readLength = if(currentLength > 0) currentLength else 0
                val countLength = responseBody.contentLength()

                FileOutputStream(file, currentLength > 0).use { output ->
                    while (inputStream.read(buffer).also { len = it } != -1) {
                        output.write(buffer, 0, len)
                        readLength += len
                        emit(DownloadStatus.Downloading(readLength, countLength))
                    }
                    output.flush()
                }
                inputStream.close()
                emit(DownloadStatus.Done(file))
            }
        }
    }
        .flowOn(Dispatchers.IO)
        .catch {
            emit(DownloadStatus.Failed(it))
        }

    private fun isFile(responseBody: ResponseBody): Boolean {
        val countLength = responseBody.contentLength()
//        val contentType = responseBody.contentType()
        return countLength != -1L
    }
}