package com.aneesh.weather.feature.weather.presentation.components

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
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.aneesh.weather.R
import com.aneesh.weather.core.util.formatTemperature
import com.aneesh.weather.core.util.toForecastDayLabel
import com.aneesh.weather.feature.weather.domain.model.DailyWeather
import com.aneesh.weather.feature.weather.presentation.theme.LocalWeatherPalette
import coil.compose.AsyncImage

@Composable
fun DailyForecastSection(weather: List<DailyWeather>) {
    val palette = LocalWeatherPalette.current
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("7-day forecast", style = MaterialTheme.typography.titleLarge, color = palette.content)
        weather.forEach { DailyItem(it) }
    }
}

@Composable
private fun DailyItem(day: DailyWeather) {
    val palette = LocalWeatherPalette.current
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = palette.cardContainer, contentColor = palette.content)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(day.date.toForecastDayLabel(), style = MaterialTheme.typography.titleMedium)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = "https:${day.icon}",
                    contentDescription = day.condition,
                    modifier = Modifier.size(42.dp)
                )
                if (day.chanceOfRain > 0) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        Icon(
                            painter = painterResource(R.drawable.ic_rain_chance),
                            contentDescription = "Chance of rain",
                            modifier = Modifier.size(14.dp),
                            tint = palette.rain
                        )
                        Text("${day.chanceOfRain}%", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
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
