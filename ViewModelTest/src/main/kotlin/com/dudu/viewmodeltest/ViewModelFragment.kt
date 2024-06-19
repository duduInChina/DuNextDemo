package com.dudu.viewmodeltest

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.dudu.common.base.fragment.BaseFragment
import com.dudu.common.base.viewmodel.BaseViewModel
import com.dudu.common.ext.launchAndRepeatWithViewLifecycle
import com.dudu.viewmodeltest.databinding.ActivityViewmodelDataviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewModelFragment: BaseFragment() {

    override val viewModel: TestViewModel by viewModels()

    override val bodyBinding by lazy {
        ActivityViewmodelDataviewBinding.inflate(layoutInflater)
    }

    override fun initView() {
        Log.d("viewmodeltest", viewModel.toString())
        bodyBinding.test.setOnClickListener {
            viewModel.setData("test")
        }
    }

    override fun initFlow() {
        launchAndRepeatWithViewLifecycle {
            viewModel.testString.observe(this@ViewModelFragment) {
                bodyBinding.tvData.text = it
                Log.d("viewmodeltest", "Fragment获取数据${it}")
            }
        }
    }

}