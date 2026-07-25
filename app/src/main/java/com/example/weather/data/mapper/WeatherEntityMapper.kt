package com.example.weather.data.mapper

import com.example.weather.data.db.WeatherEntity
import com.example.weather.domain.model.Weather

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

        updatedAt = updatedAt

    )