package com.dudu.viewmodeltest

import android.util.Log
import androidx.activity.viewModels
import com.dudu.common.base.activity.BaseActivity
import com.dudu.common.bean.Title
import com.dudu.common.ext.launchAndRepeatWithViewLifecycle
import com.dudu.common.ext.logD
import com.dudu.common.router.RouterPath
import com.dudu.viewmodeltest.databinding.ActivityViewmodelDataviewBinding
import com.therouter.TheRouter
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterPath.VIEWMODEL_TEST_FIRST)
@AndroidEntryPoint
class ViewModelFirstActivity: BaseActivity() {

    override val bodyBinding by lazy {
        ActivityViewmodelDataviewBinding.inflate(layoutInflater)
    }
    override val title = Title(text = "第一")

    override val viewModel: TestViewModel by viewModels()

    private val viewModel2: TestViewModel by viewModels()

    override fun initView() {
        bodyBinding.test.setOnClickListener {
            TheRouter.build(RouterPath.VIEWMODEL_TEST_SECOND).navigation()
//            viewModel2.setData("test")
        }
        viewModel.toString().logD(TAG)
        viewModel2.toString().logD(TAG)
    }

    override fun initFlow() {
        launchAndRepeatWithViewLifecycle {
            viewModel.testString.observe(this@ViewModelFirstActivity){
                bodyBinding.tvData.text = it
                "第一获取数据${it}".logD(TAG)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()

        "第一onRestart:${viewModel.testString.value}".logD(TAG)

    }

}