package com.aneesh.weather.presentation.components

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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.aneesh.weather.R
import com.aneesh.weather.util.formatTemperature
import com.aneesh.weather.util.toDisplayTime
import coil.compose.AsyncImage
import com.aneesh.weather.domain.model.HourlyWeather
import com.aneesh.weather.presentation.theme.LocalWeatherPalette
import com.aneesh.weather.presentation.theme.Dimens
import com.aneesh.weather.util.shouldShowRainChance
import kotlin.collections.map

@Composable
fun HourlyForecastSection(weather: List<HourlyWeather>) {
    val palette = LocalWeatherPalette.current
    Column(modifier = Modifier.padding(top = Dimens.Space24)) {
        Text(
            "Hourly forecast",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = Dimens.Space16),
            color = palette.content
        )
        TemperatureChart(weather, lineColor = palette.chartLine, gridColor = palette.chartGrid)
        LazyRow(
            contentPadding = PaddingValues(Dimens.Space16),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)
        ) {
            items(weather, key = { it.time }) { HourItem(it) }
        }
    }
}

@Composable
private fun TemperatureChart(hours: List<HourlyWeather>, lineColor: Color, gridColor: Color) {
    if (hours.size < 2) return
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.ChartHeight)
            .padding(horizontal = Dimens.Space24, vertical = Dimens.Space16)
    ) {
        val temperatures = hours.map { it.temperature.toFloat() }
        val min = temperatures.min()
        val max = temperatures.max()
        val range = (max - min).takeIf { it > 0f } ?: 1f
        val left = Dimens.Space8.toPx()
        val right = size.width - Dimens.Space8.toPx()
        val top = Dimens.Space8.toPx()
        val bottom = size.height - Dimens.Space8.toPx()

        repeat(3) { index ->
            val y = top + (bottom - top) * index / 2f
            drawLine(
                gridColor,
                start = Offset(left, y),
                end = Offset(right, y),
                strokeWidth = Dimens.ChartGridStroke.toPx()
            )
        }

        val points = temperatures.mapIndexed { index, temperature ->
            val x = left + (right - left) * index / (temperatures.lastIndex).toFloat()
            val y = bottom - ((temperature - min) / range) * (bottom - top)
            Offset(x, y)
        }
        points.zipWithNext().forEach { (start, end) ->
            drawLine(
                lineColor,
                start,
                end,
                strokeWidth = Dimens.ChartLineStroke.toPx(),
                cap = StrokeCap.Round
            )
        }
        points.forEach { point ->
            drawCircle(Color.White, radius = Dimens.ChartPointRadius.toPx(), center = point)
            drawCircle(lineColor, radius = Dimens.ChartPointInnerRadius.toPx(), center = point)
        }
    }
}

@Composable
private fun HourItem(hour: HourlyWeather) {
    val palette = LocalWeatherPalette.current
    ElevatedCard(
        modifier = Modifier.width(Dimens.HourlyCardWidth),
        colors = CardDefaults.elevatedCardColors(containerColor = palette.cardContainer, contentColor = palette.content)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = Dimens.Space16, horizontal = Dimens.Space12)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.Space6)
        ) {
            Text(hour.time.toDisplayTime(), style = MaterialTheme.typography.labelLarge)
            AsyncImage(
                model = "https:${hour.icon}",
                contentDescription = hour.condition,
                modifier = Modifier.size(Dimens.HourlyIcon)
            )
            if (shouldShowRainChance(hour.chanceOfRain)) {
                RainChance(hour.chanceOfRain)
            }
            Text(hour.temperature.formatTemperature(), style = MaterialTheme.typography.titleMedium)
            Text(hour.condition, style = MaterialTheme.typography.labelSmall, maxLines = 1)
        }
    }
}

@Composable
private fun RainChance(chance: Int) {
    val palette = LocalWeatherPalette.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Space2)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_rain_chance),
            contentDescription = "Chance of rain",
            modifier = Modifier.size(Dimens.RainIcon),
            tint = palette.rain
        )
        Text("$chance%", style = MaterialTheme.typography.labelSmall)
    }
}
