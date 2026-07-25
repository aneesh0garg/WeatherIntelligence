package com.aneesh.weather.feature.weather.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aneesh.weather.core.util.formatTemperature
import com.aneesh.weather.core.util.toDisplayTime
import com.aneesh.weather.feature.weather.domain.model.HourlyWeather

@Composable
fun HourlyForecastSection(weather: List<HourlyWeather>) {
    Column(modifier = Modifier.padding(top = 24.dp)) {
        Text("Hourly forecast", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 16.dp))
        LazyRow(contentPadding = PaddingValues(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(weather, key = { it.time }) { HourItem(it) }
        }
    }
}

@Composable
private fun HourItem(hour: HourlyWeather) {
    ElevatedCard(modifier = Modifier.width(100.dp)) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(hour.time.toDisplayTime(), style = MaterialTheme.typography.labelLarge)
            Text(hour.temperature.formatTemperature(), style = MaterialTheme.typography.titleMedium)
            Text(hour.condition, style = MaterialTheme.typography.labelSmall, maxLines = 1)
        }
    }
}
