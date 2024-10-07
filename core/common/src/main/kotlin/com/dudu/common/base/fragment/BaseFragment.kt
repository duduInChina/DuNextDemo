package com.dudu.common.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dudu.common.base.view.BaseView
import com.dudu.common.base.view.ContentViewController
import com.dudu.common.base.view.IContentView
import com.dudu.common.base.view.LoadingViewController
import com.dudu.common.bean.FailedViewStatus

abstract class BaseFragment : Fragment(), BaseView {

    private lateinit var contentViewController: IContentView

    private val loadingViewController by lazy {
        LoadingViewController(requireActivity(), rootBinding)
    }

    val rootBinding
        get() = contentViewController.getContentRootBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contentViewController = ContentViewController(
            this, inflater, container, title, bodyBinding)
        return rootBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.initViewModel(this, lifecycle, viewLifecycleOwner)
        initView()
        initFlow()
    }

    override fun onDestroy() {
        super.onDestroy()
        contentViewController.clean()
    }

    override fun goBack() {
        activity?.finish()
    }

    override fun loadingView() = loadingViewController.loadingView()


    override fun loadingDialog() = loadingViewController.loadingDialog()


    override fun loadingHide() = loadingViewController.hideLoading()

    override fun showFailedView(failedViewStatus: FailedViewStatus) =
        contentViewController.showFailedView(failedViewStatus)

    override fun getFailedViewBinding(): ViewBinding? = contentViewController.getContentFailedViewBinding()

    override fun showStatusBarSub(backgroundColor: Int) =
        contentViewController.showStatusBarSub(backgroundColor)

    override fun initFlow() {}
}