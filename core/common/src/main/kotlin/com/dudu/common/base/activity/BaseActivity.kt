package com.dudu.common.base.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dudu.common.base.view.BaseView
import com.dudu.common.base.view.ContentViewController
import com.dudu.common.base.view.LoadingViewController
import com.dudu.common.base.viewmodel.BaseViewModel
import com.dudu.common.bean.FailedViewStatus
import com.dudu.common.bean.Title
import com.therouter.TheRouter

abstract class BaseActivity : AppCompatActivity(), BaseView {

    private val contentViewController by lazy {
        ContentViewController(
            baseView = this,
            layoutInflater = layoutInflater,
            title = title,
            bodyBinding = bodyBinding
        )
    }

    private val loadingViewController by lazy {
        LoadingViewController(this, rootBinding)
    }

    val rootBinding
        get() = contentViewController.rootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        viewModel?.initViewModel(this, lifecycle, this)
        // 路由参数注入/写入
        TheRouter.inject(this)
        initView()
        initFlow()
    }

    override fun goBack() = finish()

    override fun loadingView() = loadingViewController.loadingView()


    override fun loadingDialog() = loadingViewController.loadingDialog()


    override fun loadingHide() = loadingViewController.hideLoading()


    override fun showFailedView(failedViewStatus: FailedViewStatus) =
        contentViewController.showFailedView(failedViewStatus)

    override fun getFailedViewBinding(): ViewBinding? = contentViewController.failedViewBinding

    override fun showStatusBarSub(backgroundColor: Int) =
        contentViewController.showStatusBarSub(backgroundColor)

    override fun initFlow() {}
}