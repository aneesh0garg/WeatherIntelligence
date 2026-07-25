package com.example.weather.feature.weather.presentation.home

import androidx.compose.runtime.Composable
import com.example.weather.feature.weather.domain.model.DailyWeather

@Composable
fun DailyForecast(weather: List<DailyWeather>) {
//    LazyColumn {
//        items(weather.daily) { day ->
//            DailyItem(day)
//        }
//    }
}

@Composable
private fun DailyItem(day: DailyWeather) {
    // Implement DailyItem
}
