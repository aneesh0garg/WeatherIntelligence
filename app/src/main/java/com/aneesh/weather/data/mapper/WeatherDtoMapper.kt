package com.aneesh.weather.feature.weather.data.mapper

import com.aneesh.weather.feature.weather.data.api.model.WeatherResponse
import com.aneesh.weather.feature.weather.data.db.WeatherEntity
import com.aneesh.weather.feature.weather.domain.model.DailyWeather
import com.aneesh.weather.feature.weather.domain.model.HourlyWeather

fun WeatherResponse.toEntity(): WeatherEntity {

    return WeatherEntity(
        city = location.name,
        country = location.country,
        temperature = current.temperature,
        feelsLike = current.feelsLike,
        humidity = current.humidity,
        pressure = current.pressure,
        wind = current.wind,
        condition = current.condition.text,
        icon = current.condition.icon,
        hourlyForecast =
            forecast.forecastDays
                .first()
                .hour
                .map {
                    HourlyWeather(
                        time = it.time,
                        temperature = it.temperature,
                        condition = it.condition.text,
                        icon = it.condition.icon,
                        chanceOfRain = it.chanceOfRain
                    )
                },
        dailyForecast =
            forecast.forecastDays
                .map {
                    DailyWeather(
                        date = it.date,
                        minTemp = it.day.minTemp,
                        maxTemp = it.day.maxTemp,
                        condition = it.day.condition.text,
                        icon = it.day.condition.icon,
                        chanceOfRain = it.day.chanceOfRain
                    )

                },
        updatedAt =
            System.currentTimeMillis(),
        localTime = location.localTime
    )
}
