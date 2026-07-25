package com.aneesh.weather.feature.weather.presentation.home

import androidx.compose.runtime.Composable
import com.aneesh.weather.feature.weather.domain.model.DailyWeather

@Composable
fun DailyForecastSection(weather: List<DailyWeather>) {
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
