package com.dudu.common.ext

import androidx.lifecycle.viewModelScope
import com.dudu.common.base.viewmodel.BaseViewModel
import com.dudu.common.bean.FailedViewStatus
import com.dudu.common.bean.LoadingViewStatus
import com.dudu.common.exception.ExceptionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * 通常用于网络请求中的作用域
 * Created by Dzc on 2023/5/12.
 */

fun <T> Flow<T>.lifecycle(
    baseViewModel: BaseViewModel,
    beforeCallback: (() -> Unit)? = null,
    errorCallback: ((t: Throwable) -> Unit)? = null,
    loadingView: Boolean = false,
    loadingDialog: Boolean = false,
    showFailedView: Boolean = false,
    delayTime: Long = 0,
    callback: T.() -> Unit
) {
    baseViewModel.viewModelScope.launch(Dispatchers.Main) {
        this@lifecycle.flowOn(Dispatchers.IO)
            .onStart {
                if (baseViewModel.failedViewLiveData.value !is FailedViewStatus.HiddenView) {
                    baseViewModel.setFailedViewStatus(FailedViewStatus.HiddenView)
                }
                if (loadingView) {
                    baseViewModel.setLoadingStatus(LoadingViewStatus.LoadingView)
                } else if (loadingDialog) {
                    baseViewModel.setLoadingStatus(LoadingViewStatus.LoadingDialog)
                }

                beforeCallback?.let {
                    beforeCallback()
                }
                if(delayTime > 0){
                    delay(delayTime)
                }

            }.catch { t ->
                if (showFailedView) {
                    ExceptionManager.failedLogic(baseViewModel, t, true)
                }

                errorCallback?.let {
                    errorCallback(t)
                }
            }.onCompletion {
                baseViewModel.setLoadingStatus(LoadingViewStatus.LoadingHide)
            }.collect {
                callback(it)
            }
    }
}


fun <T> Flow<T>.lifecycleLoadingView(
    baseViewModel: BaseViewModel,
    beforeCallback: (() -> Unit)? = null,
    errorCallback: ((t: Throwable) -> Unit)? = null,
    callback: T.() -> Unit
) {
    baseViewModel.viewModelScope.launch(Dispatchers.Main) {
        this@lifecycleLoadingView.flowOn(Dispatchers.IO)
            .onStart {
                beforeCallback?.let {
                    baseViewModel.setLoadingStatus(LoadingViewStatus.LoadingView)
                    beforeCallback()
                }
            }.onCompletion {
                baseViewModel.setLoadingStatus(LoadingViewStatus.LoadingHide)
            }.catch { t ->
                baseViewModel.setLoadingStatus(LoadingViewStatus.LoadingHide)
                errorCallback?.let {
                    errorCallback(t)
                }
            }.collect {
                callback(it)
            }
    }
}

fun <T> Flow<T>.lifecycleLoadingDialog(
    baseViewModel: BaseViewModel,
    beforeCallback: (() -> Unit)? = null,
    errorCallback: ((t: Throwable) -> Unit)? = null,
    callback: T.() -> Unit
) {
    baseViewModel.viewModelScope.launch(Dispatchers.Main) {
        this@lifecycleLoadingDialog.flowOn(Dispatchers.IO)
            .onStart {
                beforeCallback?.let {
                    baseViewModel.setLoadingStatus(LoadingViewStatus.LoadingDialog)
                    beforeCallback()
                }
            }.onCompletion {
                baseViewModel.setLoadingStatus(LoadingViewStatus.LoadingHide)
            }.catch { t ->
                baseViewModel.setLoadingStatus(LoadingViewStatus.LoadingHide)
                errorCallback?.let {
                    errorCallback(t)
                }
            }.collect {
                callback(it)
            }
    }
}

