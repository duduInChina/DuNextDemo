package com.dudu.common.base.view

import androidx.viewbinding.ViewBinding
import com.dudu.common.bean.FailedViewStatus
import com.dudu.common.databinding.ViewRootBinding

interface IContentView {
    fun getContentRootBinding(): ViewRootBinding

    fun showFailedView(failedViewStatus: FailedViewStatus)

    fun getContentFailedViewBinding(): ViewBinding

    fun showStatusBarSub(backgroundColor: Int)

    fun clean()
}