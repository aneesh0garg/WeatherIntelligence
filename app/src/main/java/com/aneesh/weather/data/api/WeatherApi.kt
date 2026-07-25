package com.aneesh.weather.data.api

import com.aneesh.weather.data.api.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("forecast.json")
    suspend fun getForecast(

        @Query("key")
        apiKey: String,

        @Query("q")
        city: String,

        @Query("days")
        days: Int = ApiConstants.DEFAULT_DAYS,

        @Query("aqi")
        aqi: String = ApiConstants.AQI,

        @Query("alerts")
        alerts: String = ApiConstants.ALERTS

    ): WeatherResponse
}