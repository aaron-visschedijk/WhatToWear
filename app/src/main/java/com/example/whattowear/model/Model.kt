package com.example.whattowear.model

/**
 * Underlying data model that [ForecastListFragment] and [MainForecastFragment] access
 * from [ForecastViewModel]
 */

data class WearForecast (
    val day: String,
    val subtitle: String,
    val drawable: Int,
)