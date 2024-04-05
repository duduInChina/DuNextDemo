package com.dudu.weather

import com.drake.brv.utils.BRV
import com.dudu.common.R
import com.dudu.common.base.activity.BaseActivity
import com.dudu.common.router.RouterPath
import com.dudu.common.util.StatusBarUtil
import com.dudu.common.util.getResColor
import com.dudu.weather.databinding.ActivityWeatherMainBinding
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = RouterPath.WEATHER)
class WeatherMainActivity : BaseActivity() {

    override val bodyBinding by lazy {
        ActivityWeatherMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        StatusBarUtil.setStatusBarColor(window, getResColor(R.color.blue))
        BRV.modelId = BR.m
    }
}