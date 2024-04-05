package com.dudu.common.exception

import com.dudu.common.base.viewmodel.BaseViewModel
import com.dudu.common.bean.FailedViewStatus
import com.dudu.common.util.showToast

/**
 * 功能介绍
 * Created by Dzc on 2023/6/26.
 */
class ExceptionManager {
    companion object{
        fun failedLogic(baseViewModel: BaseViewModel, t: Throwable, showFailedView: Boolean = false) {
            when (t) {
                is NetworkException ->
                    if (showFailedView)
                        baseViewModel.setFailedViewStatus(FailedViewStatus.NetworkError)
                    else showToast(FailedViewStatus.NetworkError.failedText)

                is HttpException ->
                    if (showFailedView)
                        baseViewModel.setFailedViewStatus(FailedViewStatus.HttpError)
                    else showToast(FailedViewStatus.HttpError.failedText)

                is JsonException ->
                    if (showFailedView)
                        baseViewModel.setFailedViewStatus(FailedViewStatus.DataError)
                    else showToast("数据解析异常")

                is ResultException ->
                    if (showFailedView)
                        baseViewModel.setFailedViewStatus(FailedViewStatus.DataError)
                    else showToast(t.message ?: "获取数据异常")
            }
        }
    }
}