package com.dudu.weather.ui.place

import com.dudu.common.base.mvi.Action
import com.dudu.common.base.mvi.Effect
import com.dudu.common.base.mvi.IntentViewModel
import com.dudu.common.base.mvi.State
import com.dudu.common.exception.ExceptionManager
import com.dudu.common.ext.lifecycle
import com.dudu.common.ext.lifecycleLoadingView
import com.dudu.weather.bean.PlaceResponse
import com.dudu.weather.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : IntentViewModel<PlaceAction, PlaceState, PlaceEffect>() {

    override fun initialState(): PlaceState = PlaceState.INIT

    override fun onAction(action: PlaceAction) {
        when (action) {
            is PlaceAction.LocalPlace -> localPlace()
            is PlaceAction.SavePlace -> savePlace(action.place)
            is PlaceAction.SearchPlace -> searchPlaces(action.query, action.delayTime)
        }
    }

    private fun localPlace() = weatherRepository.weatherPlacePre
        .lifecycleLoadingView(this) {
            this?.let {
                sendEffect {
                    PlaceEffect.LocalPlace(it)
                }
            }
        }

    private fun savePlace(place: PlaceResponse.Place) = weatherRepository.savePlace(place)
        .lifecycle(this) {
            sendEffect {
                PlaceEffect.SaveSuccess(place)
            }
        }
    private var showFailedView = false
    private fun searchPlaces(query: String, delayTime: Long = 0) {
        weatherRepository.placeService.searchPlace(query).lifecycle(this, {
            sendState(PlaceState.SearchLoad)
        }, {
            ExceptionManager.failedLogic(this, it, showFailedView)
        }, delayTime = delayTime) {
            showFailedView = this.places.size <= 0
            sendState(PlaceState.SearchSuccess(this.places))
        }
    }
}

sealed class PlaceAction : Action {
    // 获取保存本地的Place
    data object LocalPlace : PlaceAction()

    // 保存Place
    data class SavePlace(val place: PlaceResponse.Place) : PlaceAction()

    // 搜索Place
    data class SearchPlace(val query: String, val delayTime: Long = 0) : PlaceAction()
}

sealed class PlaceState : State {
    data object INIT : PlaceState()
    data object SearchLoad : PlaceState()
    data class SearchSuccess(val placeList: List<PlaceResponse.Place>) : PlaceState()
    data object SearchError : PlaceState()
}

sealed class PlaceEffect : Effect {
    // 返回保存本地的Place
    data class LocalPlace(val place: PlaceResponse.Place) : PlaceEffect()

    // 保存Place成功
    data class SaveSuccess(val place: PlaceResponse.Place) : PlaceEffect()
}