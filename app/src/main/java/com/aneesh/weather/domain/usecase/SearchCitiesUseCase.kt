package com.aneesh.weather.domain.usecase

import com.aneesh.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(query: String) = repository.searchCities(query)
}
