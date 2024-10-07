package com.dudu.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.dudu.common.base.view.BaseView
import com.dudu.common.base.view.ContentViewController
import com.dudu.common.base.view.IContentView
import com.dudu.common.bean.Title

/**
 * 公共配置
 */
object Constant {
    /**
     * 配置ContentView底层布局
     */
    val contentView = { baseView: BaseView,
                        layoutInflater: LayoutInflater,
                        container: ViewGroup?,
                        title: Title?,
                        bodyBinding: ViewBinding?
        ->
        ContentViewController(
            baseView = baseView,
            layoutInflater = layoutInflater,
            container = container,
            title = title,
            bodyBinding = bodyBinding,
        ) as IContentView
    }
}