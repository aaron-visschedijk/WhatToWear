package com.example.whattowear.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * JsonClass tailored to the API response on the API call made in [OpenWeatherApiService]
 */
@JsonClass(generateAdapter = true)
data class ForecastDTO(
    val current: CurrentForecastDTO,
    val daily: List<DailyForecastDTO>
)

@JsonClass(generateAdapter = true)
data class DailyForecastDTO(
    @Json(name = "feels_like")
    val temperature: TemperatureDTO,
    val weather: List<WeatherItemDTO>,
    val dt: Long,
)

@JsonClass(generateAdapter = true)
data class CurrentForecastDTO(
    @Json(name = "feels_like")
    val temperature: Double,
    val weather: List<WeatherItemDTO>,
    val dt: Long,
)

@JsonClass(generateAdapter = true)
data class TemperatureDTO(
    val day: Double,
)

@JsonClass(generateAdapter = true)
data class ForecastItemDTO(
    val temp: TemperatureDTO,
    @Json(name = "dt") val dateTime: Long,
    @Json(name = "weather") val weathers: List<WeatherItemDTO>
)

@JsonClass(generateAdapter = true)
data class WeatherItemDTO(
    val description: String,
    @Json(name = "main") val shortDescription: String
)