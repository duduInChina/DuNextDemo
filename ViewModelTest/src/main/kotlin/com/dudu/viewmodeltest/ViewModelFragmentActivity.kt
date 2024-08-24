package com.dudu.viewmodeltest

import android.util.Log
import androidx.activity.viewModels
import androidx.viewbinding.ViewBinding
import com.dudu.common.base.activity.BaseActivity
import com.dudu.common.base.viewmodel.BaseViewModel
import com.dudu.common.bean.Title
import com.dudu.common.ext.launchAndRepeatWithViewLifecycle
import com.dudu.common.ext.logD
import com.dudu.common.router.RouterPath
import com.dudu.viewmodeltest.databinding.ActivityViewmodelFragmentBinding
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterPath.VIEWMODEL_TEST_FRAGMENT)
@AndroidEntryPoint
class ViewModelFragmentActivity: BaseActivity() {

    override val title = Title(text = "Fragment")

    override val bodyBinding by lazy {
        ActivityViewmodelFragmentBinding.inflate(layoutInflater)
    }

    override val viewModel: TestViewModel by viewModels()

    override fun initView() {
        viewModel.toString().logD(TAG)
    }

    override fun initFlow() {
        launchAndRepeatWithViewLifecycle {
            viewModel.testString.observe(this@ViewModelFragmentActivity){
                bodyBinding.tvData.text = it
                "Activity获取数据${it}".logD(TAG)
            }
        }
    }
}