package com.aneesh.weather.domain.usecase

import com.aneesh.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(city: String, forceRefresh: Boolean = false) =
        repository.getWeather(city, forceRefresh)
}
