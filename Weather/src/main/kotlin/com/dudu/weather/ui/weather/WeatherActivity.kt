package com.dudu.weather.ui.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.dudu.common.R
import com.dudu.common.base.activity.BaseActivity
import com.dudu.common.ext.launchAndRepeatWithViewLifecycle
import com.dudu.common.ext.utcdate
import com.dudu.common.router.RouterPath
import com.dudu.common.util.StatusBarUtil
import com.dudu.common.util.getResColor
import com.dudu.weather.bean.PlaceResponse
import com.dudu.weather.bean.Weather
import com.dudu.weather.bean.getSky
import com.dudu.weather.databinding.ActivityWeatherBinding
import com.dudu.weather.databinding.ForecastItemBinding
import com.therouter.router.Autowired
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@Route(path = RouterPath.WEATHER_DETAIL)
@AndroidEntryPoint
class WeatherActivity : BaseActivity() {

    override val bodyBinding: ActivityWeatherBinding by lazy {
        ActivityWeatherBinding.inflate(layoutInflater)
    }
    override val viewModel: WeatherViewModel by viewModels()

    @Autowired
    lateinit var locationLng: String

    @Autowired
    lateinit var locationLat: String

    @Autowired
    lateinit var placeName: String

    override fun initView() {
        StatusBarUtil.immersiveStatusBar(window, getResColor(R.color.blue))
        viewModel.locationLng = locationLng
        viewModel.locationLat = locationLat
        viewModel.placeName = placeName

        bodyBinding.run {
            swipeRefresh.setColorSchemeResources(R.color.blue)
            swipeRefresh.setOnRefreshListener {
                refreshWeather()
            }
            inclueNow.navBtn.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                }

                override fun onDrawerOpened(drawerView: View) {
                }

                override fun onDrawerClosed(drawerView: View) {
                    val manager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    manager.hideSoftInputFromWindow(
                        drawerView.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }

                override fun onDrawerStateChanged(newState: Int) {
                }
            })
        }
    }

    override fun initFlow() {
        launchAndRepeatWithViewLifecycle {
            viewModel.state.collect {
                when (it) {
                    is WeatherState.INIT -> {
                        bodyBinding.swipeRefresh.isRefreshing = false
                        refreshWeather()
                    }

                    is WeatherState.WeatherLoad -> {
                        bodyBinding.swipeRefresh.isRefreshing = true
                    }

                    is WeatherState.WeatherInfoSuccess -> {
                        bodyBinding.swipeRefresh.isRefreshing = false
                        showWeatherInfo(it.weather)
                    }
                    is WeatherState.WeatherInfoError -> {
                        bodyBinding.swipeRefresh.isRefreshing = false
                    }
                }
            }
        }

    }

    private fun refreshWeather() {
        viewModel.sendAction(
            WeatherAction.RefreshWeather(
                viewModel.locationLng,
                viewModel.locationLat
            )
        )
    }

    fun selectPlace(place: PlaceResponse.Place) {
        bodyBinding.drawerLayout.closeDrawers()

        viewModel.locationLng = place.location.lng
        viewModel.locationLat = place.location.lat
        viewModel.placeName = place.name
        refreshWeather()
    }

    private fun showWeatherInfo(weather: Weather) {
        bodyBinding.inclueNow.run {
            placeName.text = viewModel.placeName
            val realtime = weather.realtime
            val currentTempText = "${realtime.temperature.toInt()} ℃"
            currentTemp.text = currentTempText
            currentSky.text = getSky(realtime.skycon).info
            val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
            currentAQI.text = currentPM25Text
            nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        }

        bodyBinding.inclueForecast.run {
            forecastLayout.removeAllViews()
            val daily = weather.daily
            val days = weather.daily.skycon.size
            for (i in 0 until days) {
                val skycon = daily.skycon[i]
                val temperature = daily.temperature[i]
                val viewBinding = ForecastItemBinding.inflate(
                    LayoutInflater.from(this@WeatherActivity),
                    forecastLayout,
                    false
                )
                viewBinding.dateInfo.text = skycon.date.utcdate("yyyy-MM-dd")
                val sky = getSky(skycon.value)
                viewBinding.skyIcon.setImageResource(sky.icon)
                viewBinding.skyInfo.text = sky.info
                val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
                viewBinding.temperatureInfo.text = tempText
                forecastLayout.addView(viewBinding.root)
            }
        }

        bodyBinding.inclueLifeIndex.run {
            val lifeIndex = weather.daily.lifeIndex
            coldRiskText.text = lifeIndex.coldRisk[0].desc
            dressingText.text = lifeIndex.dressing[0].desc
            ultravioletText.text = lifeIndex.ultraviolet[0].desc
            carWashingText.text = lifeIndex.carWashing[0].desc
        }
        bodyBinding.weatherLayout.visibility = View.VISIBLE

    }

    override fun onFailedViewReload() {
        refreshWeather()
    }
}