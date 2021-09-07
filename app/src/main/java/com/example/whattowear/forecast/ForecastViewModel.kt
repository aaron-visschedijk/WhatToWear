package com.example.whattowear.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whattowear.model.WearForecast
import com.example.whattowear.network.CurrentForecastDTO
import com.example.whattowear.network.DailyForecastDTO
import com.example.whattowear.network.ForecastDTO
import com.example.whattowear.network.OpenWeatherApi
import com.example.whattowear.weatherToWearForecast
import kotlinx.coroutines.launch

//CONSTANTS
val APP_ID = "bad23193c25762459e1027bbfdf9a795"
val EXCLUDE = "minutely,hourly,alerts"

//TEMPORARY CONSTANTS UNTIL I FIGURE OUT HOW TO USE SERVICES OUTSIDE THE VIEW (currently set to Copenhagen)
val LON = "55.6761"
val LAT = "12.5683"

/**
 * The [ViewModel] that is attached to [ForecastsListFragment] and [MainForecastFragment]
 */
class ForecastViewModel : ViewModel() {

    // Internal mutable data that stores the most recent API response
    private val _forecast = MutableLiveData<ForecastDTO>()

    // Internal mutable LiveData for MainForecastFragment
    private val _mainForecast = MutableLiveData<WearForecast>()

    // External immutable LiveData interface
    val mainForecast: LiveData<WearForecast> = _mainForecast

    /**
     * Updates value of [mainForecast]
     */
    private fun updateMainForecast(fc: CurrentForecastDTO) {
        _mainForecast.value = weatherToWearForecast(
            fc.weather[0].shortDescription,
            fc.weather[0].description,
            fc.temperature,
            fc.dt
        )
    }

    // Internal mutable LiveData for ForecastListFragment
    private val _subForecasts = MutableLiveData<List<WearForecast>>()

    // External immutable LiveData interface
    val subForecasts = _subForecasts

    /**
     * Updates value of [subForecasts]
     */
    private fun updateSubForecasts(forecasts: List<DailyForecastDTO>) {
        val mutableList = mutableListOf<WearForecast>()
        for (fc in forecasts.subList(1, forecasts.size)) {
            val wearForecast = weatherToWearForecast(
                fc.weather[0].shortDescription,
                fc.weather[0].description,
                fc.temperature.day,
                fc.dt
            )
            mutableList.add(wearForecast)
        }
        _subForecasts.value = mutableList
    }

    /**
     * Retrieve data upon initialization
     */
    init {
        getForecastData()
    }

    /**
     * Gets forecast data from OpenWeatherMapAPI using retrofit
     * Updates [ForecastDTO] [LiveData]
     */
    private fun getForecastData() {
        viewModelScope.launch {
            try {
                _forecast.value = OpenWeatherApi.retrofitService.getForecastDTO(
                    LON,
                    LAT,
                    EXCLUDE,
                    APP_ID
                )
                updateMainForecast(_forecast.value!!.current)
                updateSubForecasts(_forecast.value!!.daily)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}




