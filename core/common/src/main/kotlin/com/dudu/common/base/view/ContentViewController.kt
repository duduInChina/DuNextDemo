package com.dudu.common.base.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.view.contains
import androidx.viewbinding.ViewBinding
import com.dudu.common.R
import com.dudu.common.bean.FailedViewStatus
import com.dudu.common.bean.Title
import com.dudu.common.bean.TitleType
import com.dudu.common.databinding.ViewCollapsingTitleBinding
import com.dudu.common.databinding.ViewDefaultTitleBinding
import com.dudu.common.databinding.ViewFailedBinding
import com.dudu.common.databinding.ViewRootBinding
import com.dudu.common.util.DensityUtils.dp
import com.dudu.common.util.StatusBarUtil
import com.therouter.getApplicationContext

class ContentViewController(
    private val baseView: BaseView,
    private val layoutInflater: LayoutInflater,
    private val container: ViewGroup? = null,
    private val title: Title? = null,
    private val bodyBinding: ViewBinding? = null
) {

    // 建立根布局，方便加载标题，加载view，错误view
    private var _rootBinding: ViewRootBinding? = null
    val rootBinding
        get() = _rootBinding ?: run {
            _rootBinding = container?.let {
                ViewRootBinding.inflate(layoutInflater, container, false)
            } ?: ViewRootBinding.inflate(layoutInflater)
            _rootBinding!!
        }

    // CoordinatorLayout标题布局
    private var _collapsingTitleBinding: ViewCollapsingTitleBinding? = null
    private val collapsingTitleBinding
        get() = _collapsingTitleBinding ?: run {
            _collapsingTitleBinding = container?.let {
                ViewCollapsingTitleBinding.inflate(layoutInflater, container, false)
            } ?: ViewCollapsingTitleBinding.inflate(layoutInflater)
            _collapsingTitleBinding!!
        }

    // 错误显示的空布局
    private var _failedViewBinding: ViewFailedBinding? = null
    val failedViewBinding
        get() = _failedViewBinding ?: run {
            _failedViewBinding = container?.let {
                ViewFailedBinding.inflate(layoutInflater, container, false)
            } ?: ViewFailedBinding.inflate(layoutInflater)
            _failedViewBinding!!
        }

    init {
        title?.let {
            when (it.titleType) {
                TitleType.COLL -> {
                    // 加入collapsingTitle布局
                    addViewToRoot(collapsingTitleBinding.root)
                    bodyBinding?.let {
                        collapsingTitleBinding.layoutBody.addView(bodyBinding.root)
                    }
                    collapsingTitleBinding.run {
                        if (title.isBack) {
                            toolbar.setNavigationIcon(R.drawable.icon_toolbar_back)
                            toolbar.setNavigationOnClickListener {
                                baseView.goBack()
                            }
                        } else {
                            toolbarLeftView.layoutParams.width = 20.dp
                        }
                        collapsingToolbar.title= title.text
                    }
                }

                else -> {
                    // 常规普通固定Title
                    bodyBinding?.let {
                        addViewToRoot(bodyBinding.root)
                    }
                    rootBinding.run {
                        titleStub.setOnInflateListener { _, inflated ->
                            ViewDefaultTitleBinding.bind(inflated).run {
                                if(title.isBack){
                                    toolbar.setNavigationIcon(R.drawable.icon_toolbar_back)
                                    toolbar.setNavigationOnClickListener {
                                        baseView.goBack()
                                    }
                                }
                                toolbarTitleView.text = title.text
                            }
                        }
                        titleStub.inflate()
                    }
                }
            }
        } ?: run {
            // 加入到body的第一个view，以免阻挡load view
            bodyBinding?.let {
                addViewToRoot(bodyBinding.root)
            }
        }
    }

    fun addViewToRoot(view: View){
        rootBinding.layoutBody.addView(
            view,
            0,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    fun showFailedView(failedViewStatus: FailedViewStatus) = when(failedViewStatus) {
        is FailedViewStatus.HiddenView -> removeFailedView()
        else -> {
            failedViewBinding.icon.setImageResource(failedViewStatus.resId)
            failedViewBinding.textFailed.text = failedViewStatus.failedText
            // 判断添加异常布局
            val targetView = baseView.onFailedViewTarget()?:rootBinding.layoutBody
            failedViewBinding.root.let { view ->
                if(!targetView.contains(view)){
                    if(failedViewStatus.isClick){
                        view.setOnClickListener {
                            baseView.onFailedViewReload()
                        }
                    }
                    targetView.addView(view)
                }
            }
        }
    }

    private fun removeFailedView() {
        val targetView = baseView.onFailedViewTarget()?:rootBinding.layoutBody
        failedViewBinding.root.let { view ->
            if(targetView.contains(view)){
                targetView.removeView(view)
            }
        }
    }

    fun showStatusBarSub(@ColorRes backgroundColor: Int) {
        rootBinding.run {
            statusBarStub.setOnInflateListener { _, view ->
                getApplicationContext()?.let {
                    view.layoutParams.height =
                        StatusBarUtil.getStatusBarHeight(it)
                    view.setBackgroundResource(backgroundColor)
                }
            }
            statusBarStub.inflate()
        }
    }

    /**
     * 官方文档： Fragment 的存在时间比其视图长。请务必在 Fragment 的 onDestroyView() 方法中清除对绑定类实例的所有引用。
     */
    fun clean() {
        _rootBinding = null
        _collapsingTitleBinding = null
        _failedViewBinding = null
    }

}