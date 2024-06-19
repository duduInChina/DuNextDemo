package com.dudu.viewmodeltest

import android.util.Log
import androidx.viewbinding.ViewBinding
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.dudu.common.base.activity.BaseActivity
import com.dudu.common.bean.Title
import com.dudu.common.router.RouterPath
import com.dudu.viewmodeltest.databinding.ActivityViewmodelTestBinding
import com.dudu.viewmodeltest.databinding.ItemViewmodelTestBinding
import com.therouter.TheRouter
import com.therouter.router.Route

@Route(path = RouterPath.VIEWMODEL_TEST)
class ViewModelTestActivity: BaseActivity() {

    override val bodyBinding by lazy {
        ActivityViewmodelTestBinding.inflate(layoutInflater)
    }

    override val title = Title(com.dudu.common.R.string.viewmodel_test)
    override fun initView() {
        // 两个Activity注册同一个ViewModel

        bodyBinding.recyclerView.linear().setup {
            addType<ViewListData>(R.layout.item_viewmodel_test)
            R.id.cardView.onClick {
                TheRouter.build(getModel<ViewListData>().path).navigation()
            }
            onBind {
                val model = getModel<ViewListData>()
                val binding = getBinding<ItemViewmodelTestBinding>()
                binding.titleTextView.text = model.title
                binding.contentTitleView.text = model.content
            }
        }.models = listOf(
            ViewListData("Activity共用ViewModel", """
                两个Activity关联同一个ViewModel
                • ViewModel属性修改后不关联，监听也不会重复重复回调
                • 两个Activity产出的ViewModel不相同
                • Activity中创建两个类型一样ViewModel，两个ViewModel是同一内存地址
            """.trimIndent(), RouterPath.VIEWMODEL_TEST_FIRST),

            ViewListData("Fragment共用ViewModel", """
                Activity中两个Fragment关联同一个ViewModel
                • Fragment创建的ViewModel相互独立，互不影响
                • 如两个Fragment需要共享ViewModel，可以使用Activity创建的ViewModel
            """.trimIndent(), RouterPath.VIEWMODEL_TEST_FRAGMENT)
        )



    }
}

data class ViewListData(val title: String, val content: String, val path: String)