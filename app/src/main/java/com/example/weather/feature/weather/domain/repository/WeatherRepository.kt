package com.example.weather.feature.weather.domain.repository

import com.example.weather.feature.weather.domain.model.Resource
import com.example.weather.feature.weather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeather(city: String): Flow<Resource<Weather>>

}