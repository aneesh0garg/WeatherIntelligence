package com.example.weather.domain.usecase

import com.example.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(city: String) =
        repository.getWeather(city)
}