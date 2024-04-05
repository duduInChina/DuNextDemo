package com.dudu.weather.data

import androidx.datastore.core.DataStore
import com.dudu.data.datastore.WeatherPreferences
import com.dudu.data.datastore.copy
import com.dudu.network.RetrofitManager
import com.dudu.weather.BuildConfig
import com.dudu.weather.api.WeatherPlaceApi
import com.dudu.weather.api.WeatherService
import com.dudu.weather.bean.PlaceResponse
import com.dudu.weather.bean.Weather
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 大型的业务可把相关Repository做到独立业务的DataModule
 */

class WeatherRepository @Inject constructor(
    private val weatherPreferences: DataStore<WeatherPreferences>
) {

    val placeService by lazy {
        RetrofitManager.createService(WeatherPlaceApi::class.java, BuildConfig.WEATHER_BASEURL)
    }

    val weatherService by lazy {
        RetrofitManager.createService(WeatherService::class.java, BuildConfig.WEATHER_BASEURL)
    }

    // 组合请求 combine 短的请求等待长请求，zip 短请求结束就结束
    fun refreshWeather(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng, lat).combine(
            weatherService.getDailyWeather(lng, lat)
        ) { realtimeResponse, dailyResponse ->
            Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
        }

    // https://juejin.cn/post/6978060727760191524#heading-7
    // first() 为单次获取流，直接flow为持续可观察流
    val weatherPlacePre = flow {
        emit(weatherPreferences.data.firstOrNull())
    }.map {
        it?.let {
            if (it.savePlace) {
                PlaceResponse.Place(
                    name = it.place.name,
                    address = it.place.address,
                    location = PlaceResponse.Location(it.place.location.lng, it.place.location.lat)
                )
            } else null
        }
    }

    fun savePlace(place: PlaceResponse.Place) = flow {
        emit(weatherPreferences.updateData {
            it.copy {
                this.place = this.place.copy {
                    this.name = place.name
                    this.address = place.address
                    this.location = location.copy {
                        this.lat = place.location.lat
                        this.lng = place.location.lng
                    }
                }
                this.savePlace = true
            }
        })
    }
}