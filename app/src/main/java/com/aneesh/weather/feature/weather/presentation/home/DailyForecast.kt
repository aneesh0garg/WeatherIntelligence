package com.aneesh.weather.feature.weather.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aneesh.weather.core.util.formatTemperature
import com.aneesh.weather.feature.weather.domain.model.DailyWeather

@Composable
fun DailyForecastSection(weather: List<DailyWeather>) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("7-day forecast", style = MaterialTheme.typography.titleLarge)
        weather.forEach { DailyItem(it) }
    }
}

@Composable
private fun DailyItem(day: DailyWeather) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(day.date, style = MaterialTheme.typography.titleMedium)
            Text(day.condition, style = MaterialTheme.typography.bodySmall)
        }
        Text(
            "${day.minTemp.formatTemperature()}  /  ${day.maxTemp.formatTemperature()}",
            style = MaterialTheme.typography.titleMedium
        )
    }
}
