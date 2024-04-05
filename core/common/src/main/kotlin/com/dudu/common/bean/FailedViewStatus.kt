package com.dudu.common.bean

import androidx.annotation.DrawableRes
import com.dudu.common.R

/**
 * 异常布局状态
 * Created by Dzc on 2023/6/25.
 */
sealed class FailedViewStatus(val failedText: String, @DrawableRes val resId: Int, val isClick: Boolean = true) {
    data object HiddenView : FailedViewStatus("", 0) // 删除布局
    data object DataError : FailedViewStatus("出错了，请稍后重试", R.drawable.icon_data_error)
    data object EmptyError : FailedViewStatus("暂无数据", R.drawable.icon_empty_error)
    data object NetworkError : FailedViewStatus("请检查您的网络连接", R.drawable.icon_signal_wifi_off)
    data object HttpError : FailedViewStatus("服务异常", R.drawable.icon_link_off)
}
