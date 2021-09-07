package com.example.whattowear

import android.annotation.SuppressLint
import com.example.whattowear.model.WearForecast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


//CONSTANTS
const val WARM_CUTOFF = 291.15f //20 degrees celsius
const val COLD_CUTOFF = 281.15f //10 degrees celsius

/**
 * Converts information from API to presentable [WearForecast] object for the view
 */
fun weatherToWearForecast(
    weather: String,
    weatherLong: String,
    temperature: Double,
    dt: Long
): WearForecast {
    val day = dtToDay(dt)
    val advice = if (weather == "Rain") {
        R.drawable.rain1
    } else if (temperature < COLD_CUTOFF || weather == "Snow") {
        R.drawable.cold1
    } else if (temperature > WARM_CUTOFF && weather == "Clear") {
        R.drawable.sun1
    } else {
        R.drawable.cloudy
    }

    val subtitleFlair = when (advice) {
        R.drawable.rain1 -> "wear your raincoat"
        R.drawable.cold1 -> "wear your thick winter jacket"
        R.drawable.sun1 -> "wear those sick shades"
        R.drawable.cloudy -> "wear that comfy sweater"
        else -> "Well, this is very confusing"
    }

    val subtitle = "${weatherLong}, ${kelvinToCelsius(temperature)} degrees, ${subtitleFlair}"

    return WearForecast(day, subtitle, advice)
}

/**
 * Converts UNIX timestamp to day of the week in [String] format
 */
@SuppressLint("SimpleDateFormat")
fun dtToDay(dt: Long): String {
    val time = Date(dt * 1000)
    return SimpleDateFormat("EEEE").format(time)
}

/**
 * Converts [Double] kelving argument to [Int] rounded celsius argument.
 */
fun kelvinToCelsius(kelvin: Double): Int {
    return (kelvin - 273.15f).roundToInt()
}