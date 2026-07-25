package com.aneesh.weather.feature.weather.domain.repository

import com.aneesh.weather.feature.weather.domain.model.Resource
import com.aneesh.weather.feature.weather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeather(city: String): Flow<Resource<Weather>>

}