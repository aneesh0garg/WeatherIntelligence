package com.aneesh.weather.presentation.components

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
import androidx.compose.ui.res.stringResource
import com.aneesh.weather.R
import com.aneesh.weather.util.formatTemperature
import com.aneesh.weather.util.toForecastDayLabel
import coil.compose.AsyncImage
import com.aneesh.weather.domain.model.DailyWeather
import com.aneesh.weather.presentation.theme.LocalWeatherPalette
import com.aneesh.weather.presentation.theme.Dimens
import com.aneesh.weather.util.shouldShowRainChance

@Composable
fun DailyForecastSection(weather: List<DailyWeather>) {
    val palette = LocalWeatherPalette.current
    Column(
        modifier = Modifier.padding(Dimens.Space16),
        verticalArrangement = Arrangement.spacedBy(Dimens.Space12)
    ) {
        Text(stringResource(R.string._7_day_forecast), style = MaterialTheme.typography.titleLarge, color = palette.content)
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
            modifier = Modifier.padding(horizontal = Dimens.Space16, vertical = Dimens.Space12),
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
                    modifier = Modifier.size(Dimens.ForecastIcon)
                )
                if (shouldShowRainChance(day.chanceOfRain)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimens.Space2)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_rain_chance),
                            contentDescription = stringResource(R.string.chance_of_rain),
                            modifier = Modifier.size(Dimens.RainIcon),
                            tint = palette.rain
                        )
                        Text("${day.chanceOfRain}%", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
            Column(
                modifier = Modifier.padding(start = Dimens.Space12),
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
