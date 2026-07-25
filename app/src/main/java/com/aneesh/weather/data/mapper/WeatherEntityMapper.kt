package com.aneesh.weather.data.mapper

import com.aneesh.weather.data.db.WeatherEntity
import com.aneesh.weather.domain.model.Weather

fun WeatherEntity.toDomain() =
    Weather(
        city = city,
        country = country,
        temperature = temperature,
        feelsLike = feelsLike,
        humidity = humidity,
        pressure = pressure,
        wind = wind,
        condition = condition,
        icon = icon,
        hourly = hourlyForecast,
        daily = dailyForecast,
        updatedAt = updatedAt,
        localTime = localTime
    )