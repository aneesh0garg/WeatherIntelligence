package com.aneesh.weather.feature.weather.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.aneesh.weather.R
import com.aneesh.weather.core.util.formatTemperature
import com.aneesh.weather.core.util.toDisplayTime
import com.aneesh.weather.feature.weather.domain.model.HourlyWeather
import coil.compose.AsyncImage

@Composable
fun HourlyForecastSection(
    weather: List<HourlyWeather>,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column(modifier = Modifier.padding(top = 24.dp)) {
        Text(
            "Hourly forecast",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = contentColor
        )
        TemperatureChart(weather, lineColor = contentColor, gridColor = contentColor.copy(alpha = 0.35f))
        LazyRow(contentPadding = PaddingValues(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(weather, key = { it.time }) { HourItem(it, containerColor, contentColor) }
        }
    }
}

@Composable
private fun TemperatureChart(hours: List<HourlyWeather>, lineColor: Color, gridColor: Color) {
    if (hours.size < 2) return
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(152.dp)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        val temperatures = hours.map { it.temperature.toFloat() }
        val min = temperatures.min()
        val max = temperatures.max()
        val range = (max - min).takeIf { it > 0f } ?: 1f
        val left = 8.dp.toPx()
        val right = size.width - 8.dp.toPx()
        val top = 8.dp.toPx()
        val bottom = size.height - 8.dp.toPx()

        repeat(3) { index ->
            val y = top + (bottom - top) * index / 2f
            drawLine(gridColor, start = androidx.compose.ui.geometry.Offset(left, y), end = androidx.compose.ui.geometry.Offset(right, y), strokeWidth = 1.dp.toPx())
        }

        val points = temperatures.mapIndexed { index, temperature ->
            val x = left + (right - left) * index / (temperatures.lastIndex).toFloat()
            val y = bottom - ((temperature - min) / range) * (bottom - top)
            androidx.compose.ui.geometry.Offset(x, y)
        }
        points.zipWithNext().forEach { (start, end) ->
            drawLine(lineColor, start, end, strokeWidth = 3.dp.toPx(), cap = StrokeCap.Round)
        }
        points.forEach { point ->
            drawCircle(Color.White, radius = 5.dp.toPx(), center = point)
            drawCircle(lineColor, radius = 3.dp.toPx(), center = point)
        }
    }
}

@Composable
private fun HourItem(hour: HourlyWeather, containerColor: Color, contentColor: Color) {
    ElevatedCard(
        modifier = Modifier.width(100.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = containerColor, contentColor = contentColor)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(hour.time.toDisplayTime(), style = MaterialTheme.typography.labelLarge)
            AsyncImage(
                model = "https:${hour.icon}",
                contentDescription = hour.condition,
                modifier = Modifier.size(38.dp)
            )
            if (hour.chanceOfRain > 0) {
                RainChance(hour.chanceOfRain)
            }
            Text(hour.temperature.formatTemperature(), style = MaterialTheme.typography.titleMedium)
            Text(hour.condition, style = MaterialTheme.typography.labelSmall, maxLines = 1)
        }
    }
}

@Composable
private fun RainChance(chance: Int) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        Icon(
            painter = painterResource(R.drawable.ic_rain_chance),
            contentDescription = "Chance of rain",
            modifier = Modifier.size(14.dp),
            tint = Color(0xFF59C8FF)
        )
        Text("$chance%", style = MaterialTheme.typography.labelSmall)
    }
}
