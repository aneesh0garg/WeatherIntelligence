package com.aneesh.weather.feature.weather.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aneesh.weather.core.util.formatTemperature
import com.aneesh.weather.core.util.toForecastDayLabel
import com.aneesh.weather.feature.weather.domain.model.DailyWeather
import coil.compose.AsyncImage

@Composable
fun DailyForecastSection(
    weather: List<DailyWeather>,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("7-day forecast", style = MaterialTheme.typography.titleLarge, color = contentColor)
        weather.forEach { DailyItem(it, containerColor, contentColor) }
    }
}

@Composable
private fun DailyItem(day: DailyWeather, containerColor: Color, contentColor: Color) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = containerColor, contentColor = contentColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(day.date.toForecastDayLabel(), style = MaterialTheme.typography.titleMedium)
                Text("${day.chanceOfRain}% rain", style = MaterialTheme.typography.bodySmall)
            }
            AsyncImage(
                model = "https:${day.icon}",
                contentDescription = day.condition,
                modifier = Modifier.size(42.dp)
            )
            Column(
                modifier = Modifier.padding(start = 12.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(day.condition, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                Text(
                    "${day.minTemp.formatTemperature()}  /  ${day.maxTemp.formatTemperature()}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
