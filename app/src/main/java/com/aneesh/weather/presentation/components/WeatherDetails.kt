package com.aneesh.weather.presentation.components

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
import androidx.compose.ui.unit.dp
import com.aneesh.weather.domain.model.Weather
import com.aneesh.weather.presentation.theme.LocalWeatherPalette
import com.aneesh.weather.presentation.theme.Dimens
import com.aneesh.weather.util.formatPressure
import com.aneesh.weather.util.formatWindSpeed
import com.aneesh.weather.util.toDisplayTime

@Composable
fun WeatherDetailsCard(weather: Weather) {
    val palette = LocalWeatherPalette.current
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.Space16),
        colors = CardDefaults.elevatedCardColors(containerColor = palette.cardContainer, contentColor = palette.content)
    ) {
        Column(
            modifier = Modifier.padding(Dimens.Space20),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space12)
        ) {
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
