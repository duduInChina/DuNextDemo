package com.dudu.weather.ui.place

import com.dudu.data.datastore.WeatherPreferences
import com.dudu.weather.bean.PlaceResponse
import com.dudu.weather.data.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class PlaceViewModelTest {

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var viewModel: PlaceViewModel

    private val place = PlaceResponse.Place("Test", PlaceResponse.Location("", ""), "")

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = PlaceViewModel(weatherRepository)
    }


    // 验证了action-effect 事件是否回调，存入的数据place是否和回调的相同
    @Test
    fun `action LocalPlace test`() = runTest {

        every { weatherRepository.weatherPlacePre } returns flow {
            emit(place)
        }

        launch(UnconfinedTestDispatcher()) {
            viewModel.effect.collect {
                if (it is PlaceEffect.LocalPlace) {
                    println("effect:$it")
                    assertEquals(place, it.place)
                }
                this.cancel()
            }
        }

        viewModel.sendAction(PlaceAction.LocalPlace)
    }

    @Test
    fun `action SavePlace test`() = runTest {

        every { weatherRepository.savePlace(place) } returns flow {
            emit(WeatherPreferences.getDefaultInstance())
        }

        launch(UnconfinedTestDispatcher()) {
            viewModel.effect.collect {
                if (it is PlaceEffect.SaveSuccess) {
                    println("effect:$it")
                    assertEquals(place, it.place)
                }
                this.cancel()
            }
        }

        viewModel.sendAction(PlaceAction.SavePlace(place))
    }

    @Test
    fun `action SearchPlace test`() = runTest {

        every { weatherRepository.placeService.searchPlace("Test") } returns flow{
            emit(PlaceResponse(mutableListOf(), "", ""))
        }

        launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect {
                println(it)
                when(it){
                    is PlaceState.INIT -> assertIs<PlaceState.INIT>(it)
                    is PlaceState.SearchLoad -> assertIs<PlaceState.SearchLoad>(it)
                    is PlaceState.SearchError -> assertIs<PlaceState.SearchError>(it)
                    is PlaceState.SearchSuccess -> {
                        assertIs<PlaceState.SearchSuccess>(it)
                        this.cancel()
                    }
                }

            }
        }

        viewModel.sendAction(PlaceAction.SearchPlace("Test", 0))
    }

}