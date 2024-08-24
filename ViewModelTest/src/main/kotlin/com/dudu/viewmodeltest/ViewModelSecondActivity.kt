package com.dudu.viewmodeltest

import android.util.Log
import androidx.activity.viewModels
import com.dudu.common.base.activity.BaseActivity
import com.dudu.common.bean.Title
import com.dudu.common.ext.launchAndRepeatWithViewLifecycle
import com.dudu.common.ext.logD
import com.dudu.common.router.RouterPath
import com.dudu.viewmodeltest.databinding.ActivityViewmodelDataviewBinding
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterPath.VIEWMODEL_TEST_SECOND)
@AndroidEntryPoint
class ViewModelSecondActivity : BaseActivity() {
    override val bodyBinding by lazy {
        ActivityViewmodelDataviewBinding.inflate(layoutInflater)
    }
    override val title = Title(text = "第二")
    override val viewModel: TestViewModel by viewModels()

    override fun initView() {
        bodyBinding.test.setOnClickListener {
            viewModel.setData("test")
        }
        viewModel.toString().logD(TAG)
    }

    override fun initFlow() {
        launchAndRepeatWithViewLifecycle {
            viewModel.testString.observe(this@ViewModelSecondActivity){
                bodyBinding.tvData.text = it
                "第二获取数据${it}".logD(TAG)
            }
        }
    }
}