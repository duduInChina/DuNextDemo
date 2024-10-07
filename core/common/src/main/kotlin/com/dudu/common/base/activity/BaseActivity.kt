package com.dudu.common.base.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dudu.common.Constant
import com.dudu.common.base.view.BaseView
import com.dudu.common.base.view.ContentViewController
import com.dudu.common.base.view.LoadingViewController
import com.dudu.common.bean.FailedViewStatus
import com.dudu.common.util.ActivityCollector
import com.therouter.TheRouter
import java.lang.ref.WeakReference

abstract class BaseActivity : AppCompatActivity(), BaseView {

    private val contentViewController by lazy {
        Constant.contentView(
            this, layoutInflater, null, title, bodyBinding
        )
    }

    private val loadingViewController by lazy {
        LoadingViewController(this, rootBinding)
    }

    val rootBinding
        get() = contentViewController.getContentRootBinding()

    private var weakRefActivity: WeakReference<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        viewModel?.initViewModel(this, lifecycle, this)
        // 路由参数注入/写入
        TheRouter.inject(this)

        weakRefActivity = WeakReference(this)
        ActivityCollector.add(weakRefActivity)

        initView()
        initFlow()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.remove(weakRefActivity)
    }

    override fun goBack() = finish()

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