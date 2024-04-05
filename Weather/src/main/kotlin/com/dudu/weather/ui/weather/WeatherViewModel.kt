package com.dudu.weather.ui.weather

import com.dudu.common.base.mvi.Action
import com.dudu.common.base.mvi.Effect
import com.dudu.common.base.mvi.IntentViewModel
import com.dudu.common.base.mvi.State
import com.dudu.common.exception.ExceptionManager
import com.dudu.common.ext.lifecycle
import com.dudu.weather.bean.Weather
import com.dudu.weather.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : IntentViewModel<WeatherAction, WeatherState, WeatherEffect>() {
    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    override fun initialState() = WeatherState.INIT

    override fun onAction(action: WeatherAction) {
        when (action) {
            is WeatherAction.RefreshWeather -> refreshWeather(action.lng, action.lat)
        }
    }

    private fun refreshWeather(lng: String, lat: String) {
        weatherRepository.refreshWeather(lng, lat).lifecycle(this, {
            sendState(WeatherState.WeatherLoad)
        }, {
            sendState(WeatherState.WeatherInfoError)
            ExceptionManager.failedLogic(this, it, false)
        }, showFailedView = true) {
            sendState(WeatherState.WeatherInfoSuccess(this))
        }
    }
}

sealed class WeatherAction : Action {
    // 刷新
    data class RefreshWeather(val lng: String, val lat: String) : WeatherAction()
}

sealed class WeatherState : State {
    data object INIT : WeatherState()
    data object WeatherLoad : WeatherState()
    data class WeatherInfoSuccess(val weather: Weather) : WeatherState()
    data object WeatherInfoError : WeatherState()
}

sealed class WeatherEffect : Effect {

}
