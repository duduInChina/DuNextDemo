package com.dudu.common.base.viewmodel

import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dudu.common.base.activity.BaseActivity
import com.dudu.common.base.view.BaseView
import com.dudu.common.bean.FailedViewStatus
import com.dudu.common.bean.LoadingViewStatus

open class BaseViewModel : ViewModel(), IBaseViewModel {
    // internal 模块可见
    // 显示loadingView的Ui
    // internal val loadingViewLiveData = MutableLiveData(false)

    // 显示loadingDialog
    // internal val loadingDialogLiveData = MutableLiveData(false)

    // 隐藏Loading
    // internal val loadingHideLiveData = MutableLiveData(false)

    private val _loadingViewLiveData = MutableLiveData<LoadingViewStatus>()
    val loadingViewLiveData: LiveData<LoadingViewStatus> = _loadingViewLiveData

    // 错误Ui
    private val _failedViewLiveData = MutableLiveData<FailedViewStatus>(FailedViewStatus.HiddenView)
    val failedViewLiveData: LiveData<FailedViewStatus> = _failedViewLiveData

    fun setLoadingStatus(loadState: LoadingViewStatus) {
        _loadingViewLiveData.postValue(loadState)
    }

    fun setFailedViewStatus(failedViewState: FailedViewStatus) {
        _failedViewLiveData.postValue(failedViewState)
    }

    /**
     * 在BaseActivity中调用初始化
     */
    fun initViewModel(baseView: BaseView, lifecycle: Lifecycle, owner: LifecycleOwner) {
        baseView.run {
            lifecycle.addObserver(this@BaseViewModel)
//            loadingViewLiveData.observe(owner) {
//                if (it) loadingView() else loadingHide()
//            }
//            loadingDialogLiveData.observe(owner) {
//                if (it) loadingDialog() else loadingHide()
//            }
//            loadingHideLiveData.observe(owner) {
//                loadingHide()
//            }
            loadingViewLiveData.observe(owner) {
                when (it) {
                    LoadingViewStatus.LoadingView -> loadingView()
                    LoadingViewStatus.LoadingDialog -> loadingDialog()
                    LoadingViewStatus.LoadingHide -> loadingHide()
                }
            }

            failedViewLiveData.observe(owner) {
                showFailedView(it)
            }
        }
    }
}