package com.dudu.weather.ui.place

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.dudu.common.base.fragment.BaseFragment
import com.dudu.common.bean.FailedViewStatus
import com.dudu.common.ext.launchAndRepeatWithViewLifecycle
import com.dudu.common.router.RouterPath
import com.dudu.weather.R
import com.dudu.weather.WeatherMainActivity
import com.dudu.weather.bean.PlaceResponse
import com.dudu.weather.data.WeatherRepository
import com.dudu.weather.databinding.FragmentPlaceBinding
import com.dudu.weather.ui.weather.WeatherActivity
import com.therouter.TheRouter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 选择天气地区
 * 改用brv实现列表
 * 进入WeatherMainActivity时判断，是否已经选择地区
 * 或者侧边栏进入界面
 */
@AndroidEntryPoint
class PlaceBrvFragment : BaseFragment() {


    override val viewModel: PlaceViewModel by viewModels()
    override val bodyBinding: FragmentPlaceBinding by lazy {
        FragmentPlaceBinding.inflate(layoutInflater)
    }

    override fun initView() {
        if (activity is WeatherMainActivity) {
            viewModel.sendAction(PlaceAction.LocalPlace)
        } else {
            showStatusBarSub(com.dudu.common.R.color.blue)
        }

        bodyBinding.run {
            recyclerView.linear().setup {
                addType<PlaceResponse.Place>(R.layout.item_place_bind)
                R.id.item.onClick {
                    selectPlace(getModel())
                }
            }

            searchPlaceEdit.addTextChangedListener { editable ->
                val content = editable.toString()
                if (content.isNotEmpty()) {
                    viewModel.sendAction(PlaceAction.SearchPlace(content))
                } else {
                    viewModel.sendState(PlaceState.INIT)
                }
            }
        }
    }

    private fun selectPlace(place: PlaceResponse.Place) {
        viewModel.sendAction(PlaceAction.SavePlace(place))
    }

    private fun gotoWeather(
        place: PlaceResponse.Place
    ) {
        TheRouter.build(RouterPath.WEATHER_DETAIL)
            .withString("locationLng", place.location.lng)
            .withString("locationLat", place.location.lat)
            .withString("placeName", place.name)
            .navigation()
        activity?.finish()
    }

    override fun initFlow() {
        launchAndRepeatWithViewLifecycle {
            viewModel.state.collect { state ->
                when (state) {
                    is PlaceState.INIT -> {
                        bodyBinding.run {
                            recyclerView.visibility = View.GONE
                            bgImageView.visibility = View.VISIBLE
                            bodyBinding.recyclerView.models = mutableListOf()

                            if (viewModel.failedViewLiveData.value !is FailedViewStatus.HiddenView) {
                                viewModel.setFailedViewStatus(FailedViewStatus.HiddenView)
                            }
                        }
                    }
                    is PlaceState.SearchLoad -> {
                        bodyBinding.searchPlaceLoading.visibility = View.VISIBLE
                    }
                    is PlaceState.SearchSuccess -> {
                        bodyBinding.run {
                            searchPlaceLoading.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                            bgImageView.visibility = View.GONE
                            bodyBinding.recyclerView.models = state.placeList
                        }
                    }

                    PlaceState.SearchError -> {
                        bodyBinding.searchPlaceLoading.visibility = View.GONE
                    }
                }
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is PlaceEffect.LocalPlace -> gotoWeather(effect.place)
                    is PlaceEffect.SaveSuccess -> {
                        val activity = this@PlaceBrvFragment.activity
                        if (activity is WeatherActivity) {
                            activity.selectPlace(effect.place)
                        } else {
                            gotoWeather(effect.place)
                        }
                    }
                }
            }
        }

    }

    override fun onFailedViewTarget() = bodyBinding.contentFrameLayout

    override fun onFailedViewReload() {
        bodyBinding.run {
            val content = searchPlaceEdit.text.toString()
            if (content.isNotEmpty()) {
                viewModel.sendAction(PlaceAction.SearchPlace(content, 2000))
            } else {
                viewModel.sendState(PlaceState.INIT)
            }
        }
    }

}