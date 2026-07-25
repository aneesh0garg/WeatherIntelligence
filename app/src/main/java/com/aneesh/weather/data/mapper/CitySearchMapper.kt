package com.aneesh.weather.data.mapper

import com.aneesh.weather.data.api.model.CitySearchDto
import com.aneesh.weather.domain.model.CitySuggestion

fun CitySearchDto.toDomain() = CitySuggestion(
    city = name,
    region = region,
    country = country,
    latitude = latitude,
    longitude = longitude
)
