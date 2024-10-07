package com.dudu.download.data

import com.dudu.common.ext.logD
import com.dudu.download.data.model.DownloadStatus
import com.dudu.download.data.model.DownloadTaskData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/09/24
 *     desc   : 每个文件下载的任务
 * </pre>
 */
class DownloadTask(
    val taskData: DownloadTaskData,
    private val repository: DownloadRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    // 任务状态回调
    private var _state: MutableStateFlow<DownloadStatus> = MutableStateFlow(DownloadStatus.None)
    val state: StateFlow<DownloadStatus> = _state

    private var job: Job? = null

    fun run() {
        job = scope.launch {
            repository.download(taskData)
                .collectLatest { // 防止上游发射过快忽略旧数据
                    it.toString().logD("DownloadTask")
                    _state.value = it
                }
        }
    }

    /**
     * 取消任务
     */
    fun cancel() {
        job?.cancel()
    }

}