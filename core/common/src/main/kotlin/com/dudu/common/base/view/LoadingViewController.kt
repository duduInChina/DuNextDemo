package com.dudu.common.base.view

import android.content.Context
import android.view.View
import com.dudu.common.databinding.LayoutLoadingBinding
import com.dudu.common.databinding.ViewRootBinding
import com.dudu.common.widget.MiniLoadingDialog

class LoadingViewController(
    private val context: Context,
    private val rootBinding: ViewRootBinding
) {

    private var isLoadingViewInflate = false
    private var dialog: MiniLoadingDialog? = null

    private lateinit var loadingBinding: LayoutLoadingBinding

    fun loadingView() {
        if (isLoadingViewInflate) {
            loadingBinding.loadingFrame.visibility = View.VISIBLE
        } else {
            rootBinding.loadingStub.setOnInflateListener { _, view ->
                loadingBinding = LayoutLoadingBinding.bind(view)
                loadingBinding.run {
                    isLoadingViewInflate = true
                    loadingFrame.visibility = View.VISIBLE
                }
            }
            rootBinding.loadingStub.inflate()
        }
    }

    fun loadingDialog(tip: String = "") {
        dialog?.let {
            if (it.isShowing) {
                return
            }
        }
        dialog = MiniLoadingDialog(context, tip = tip)
        dialog?.show()
    }

    fun hideLoading() {
        dialog?.let {
            it.dismiss()
            dialog = null
        }
        if (isLoadingViewInflate) {
            loadingBinding.loadingFrame.visibility = View.GONE
        }
    }

}