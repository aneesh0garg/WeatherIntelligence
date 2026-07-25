package com.aneesh.weather.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.aneesh.weather.R
import com.aneesh.weather.domain.model.Weather
import com.aneesh.weather.presentation.theme.Dimens
import com.aneesh.weather.presentation.theme.LocalWeatherPalette
import com.aneesh.weather.util.formatTemperature

@Composable
fun CurrentWeatherCard(
    weather: Weather,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    val palette = LocalWeatherPalette.current
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.Space16),
        colors = CardDefaults.elevatedCardColors(
            containerColor = palette.cardContainer,
            contentColor = palette.content
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Space24),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                weather.city,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                Modifier.height(Dimens.Space16)
            )

            AsyncImage(
                model = "https:${weather.icon}",
                contentDescription = null,
                modifier = Modifier.size(Dimens.WeatherIcon)

            )

            Text(
                weather.temperature.formatTemperature(),
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                weather.condition,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                Modifier.height(Dimens.Space12)
            )

            Text(
                "Feels like ${weather.feelsLike.toInt()}°"
            )

            WeatherOutlineButton(
                onClick = onToggleFavorite,
                modifier = Modifier.padding(top = Dimens.Space12),
                text = if (isFavorite) stringResource(R.string.remove_from_favorites) else stringResource(R.string.save_to_favorites)
            )
        }
    }
}
