package com.dudu.common.bean

sealed class LoadingViewStatus {
    // 显示loadingView的Ui
    data object LoadingView : LoadingViewStatus()
    // 显示loadingDialog
    data object LoadingDialog : LoadingViewStatus()
    // 隐藏Loading
    data object LoadingHide : LoadingViewStatus()
}