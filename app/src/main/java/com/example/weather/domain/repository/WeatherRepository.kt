package com.example.weather.domain.repository

import com.example.weather.domain.model.Resource
import com.example.weather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeather(city: String): Flow<Resource<Weather>>

}