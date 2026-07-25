package com.aneesh.weather.feature.weather.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aneesh.weather.core.util.formatPressure
import com.aneesh.weather.core.util.formatWindSpeed
import com.aneesh.weather.core.util.toDisplayTime
import com.aneesh.weather.feature.weather.domain.model.Weather

@Composable
fun WeatherDetailsCard(
    weather: Weather,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = containerColor, contentColor = contentColor)
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Weather details", style = MaterialTheme.typography.titleLarge)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Detail("Humidity", "${weather.humidity}%")
                Detail("Wind", weather.wind.formatWindSpeed())
                Detail("Pressure", weather.pressure.formatPressure())
            }
            Text("Local time: ${weather.localTime.toDisplayTime()}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun Detail(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value, style = MaterialTheme.typography.titleMedium)
    }
}
