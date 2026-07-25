package com.example.weather.feature.weather.presentation.home

import androidx.compose.runtime.Composable
import com.example.weather.feature.weather.domain.model.HourlyWeather

@Composable
fun HourlyForecast(weather: List<HourlyWeather>) {
//    LazyRow {
//        items(weather.hourly) { hour ->
//            HourItem(hour)
//        }
//    }
}

@Composable
private fun HourItem(hour: HourlyWeather) {
    // Implement HourItem
}
