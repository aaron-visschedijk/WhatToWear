package com.example.whattowear.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL =
    "https://api.openweathermap.org/"

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getForecaseDTO] method
 */
interface OpenWeatherApiService {
    /**
     * Returns a [ForecastDTO] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "data/2.5/onecall?" endpoint will be requested with the GET
     * The @Query annotations indicate that the lat, lon, exclude and appid parameters are passed
     * HTTP method
     */
    @GET("data/2.5/onecall?")
    suspend fun getForecastDTO(@Query("lat") lat: String, @Query("lon") lon: String, @Query("exclude") exclude: String, @Query("appid") app_id: String) : ForecastDTO
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object OpenWeatherApi {
    val retrofitService: OpenWeatherApiService by lazy { retrofit.create(OpenWeatherApiService::class.java) }
}
