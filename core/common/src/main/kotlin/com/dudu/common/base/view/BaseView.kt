package com.dudu.common.base.view

import android.util.Log
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.viewbinding.ViewBinding
import com.dudu.common.base.viewmodel.BaseViewModel
import com.dudu.common.bean.FailedViewStatus
import com.dudu.common.bean.Title

interface BaseView {
    val title: Title?
        get() = null

    val bodyBinding: ViewBinding?
        get() = null

    val viewModel: BaseViewModel?
        get() = null

    /**
     * 返回按钮返回事件
     */
    fun goBack()

    /**
     * 页面加载视图显示
     */
    fun loadingView()

    /**
     * 页面加载Dialog形式显示
     */
    fun loadingDialog()

    /**
     * 隐藏加载状态
     */
    fun loadingHide()

    /**
     * 显示异常布局，设置显示状态
     */
    fun showFailedView(failedViewStatus: FailedViewStatus)

    /**
     * 获得异常布局的Binding
     */
    fun getFailedViewBinding(): ViewBinding?

    /**
     * 点击异常布局回调
     */
    fun onFailedViewReload() {
        Log.d("FailedView", "click failed view reload")
    }

    /**
     * 目标异常布局加载到的View，默认是rootView
     */
    fun onFailedViewTarget(): ViewGroup? = null

    /**
     * 通常进行immersiveStatusBar，把布局顶到状态栏，才会按需设置
     */
    fun showStatusBarSub(@ColorRes backgroundColor: Int)

    /**
     * 初始化view
     */
    fun initView()

    /**
     * 监听LifeData
     */
    fun initFlow()

}