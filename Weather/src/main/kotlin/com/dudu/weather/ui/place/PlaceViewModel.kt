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

    override fun initialState(): PlaceState = PlaceState()

    override fun onAction(action: PlaceAction) {
        when (action) {
            is PlaceAction.LocalPlace -> localPlace()
            is PlaceAction.SearchPlace -> searchPlaces(action.query, action.delayTime)
            is PlaceAction.SavePlace -> savePlace(action.place)
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

    private fun searchPlaces(query: String, delayTime: Long = 0) {
        weatherRepository.placeService.searchPlace(query).lifecycle(this, loadingView = true, errorCallback = {
            ExceptionManager.failedLogic(this, it, state.value.showFailedView)
        }, delayTime = delayTime) {
            state.value.showFailedView = this.places.size <= 0
            updateState {
                copy(
                    placeList = this@lifecycle.places
                )
            }
        }
    }

    private fun savePlace(place: PlaceResponse.Place) = weatherRepository.savePlace(place)
        .lifecycle(this) {
            sendEffect {
                PlaceEffect.SaveSuccess(place)
            }
        }
}

sealed class PlaceAction : Action {
    // 获取保存本地的Place
    data object LocalPlace : PlaceAction()

    // 搜索Place
    data class SearchPlace(val query: String, val delayTime: Long = 0) : PlaceAction()

    // 保存Place
    data class SavePlace(val place: PlaceResponse.Place) : PlaceAction()

}
data class PlaceState(
    var placeList: List<PlaceResponse.Place> = listOf(),
    var showFailedView: Boolean = false
) : State

sealed class PlaceEffect : Effect {
    // 返回保存本地的Place
    data class LocalPlace(val place: PlaceResponse.Place) : PlaceEffect()

    // 保存Place成功
    data class SaveSuccess(val place: PlaceResponse.Place) : PlaceEffect()
}