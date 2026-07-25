package com.aneesh.weather.feature.weather.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aneesh.weather.feature.weather.domain.model.Weather
import com.aneesh.weather.feature.weather.presentation.components.WeatherSearchBar
import com.aneesh.weather.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteCities by viewModel.favoriteCities.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {

        when (val state = uiState) {
            is HomeUiState.Loading -> {
                LoadingScreen()
            }

            is HomeUiState.Error -> {
                ErrorScreen(state.message)
            }

            is HomeUiState.Success -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(state.weather.condition.weatherColors()))
                ) {
                    PullToRefreshBox(
                        isRefreshing = false,
                        onRefresh = {
                            viewModel.onEvent(HomeEvent.Refresh)
                        }
                    ) {
                        WeatherContent(
                            weather = state.weather,
                            isOffline = state.isOffline,
                            onSearch = { viewModel.onEvent(HomeEvent.SearchCity(it)) },
                            onTestAlert = { viewModel.onEvent(HomeEvent.SendTestAlert) },
                            favoriteCities = favoriteCities,
                            onFavoriteSelected = { viewModel.onEvent(HomeEvent.SearchCity(it)) },
                            onToggleFavorite = { viewModel.onEvent(HomeEvent.ToggleFavorite) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message)
    }
}

@Composable
private fun WeatherContent(
    weather: Weather,
    isOffline: Boolean,
    onSearch: (String) -> Unit,
    onTestAlert: () -> Unit,
    favoriteCities: List<String>,
    onFavoriteSelected: (String) -> Unit,
    onToggleFavorite: () -> Unit
) {
    val weatherColors = weather.condition.weatherColors()
    LazyColumn {
        item {
            WeatherSearchBar(initialValue = weather.city, onSearch = onSearch)
        }
        if (favoriteCities.isNotEmpty()) {
            item {
                FavoriteCitiesSection(favoriteCities, onFavoriteSelected)
            }
        }
        if (isOffline) {
            item {
                Surface(color = MaterialTheme.colorScheme.tertiaryContainer) {
                    Text(
                        text = "Showing the last saved forecast. Connect to refresh.",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }
        item {
            WeatherHero(
                weather = weather,
                isFavorite = favoriteCities.any { it.equals(weather.city, ignoreCase = true) },
                onToggleFavorite = onToggleFavorite,
                containerColor = weatherColors.last()
            )
        }
        item {
            WeatherDetailsCard(
                weather = weather,
                containerColor = weatherColors.last(),
                contentColor = androidx.compose.ui.graphics.Color.White
            )
        }
        if (BuildConfig.DEBUG) {
            item {
                Button(
                    onClick = onTestAlert,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = weatherColors.last(),
                        contentColor = androidx.compose.ui.graphics.Color.White
                    )
                ) { Text("Send test severe-weather alert") }
            }
        }
        item {
            HourlyForecastSection(
                weather = weather.hourly,
                containerColor = weatherColors.last(),
                contentColor = androidx.compose.ui.graphics.Color.White
            )
        }
        item {
            DailyForecastSection(
                weather = weather.daily,
                containerColor = weatherColors.last(),
                contentColor = androidx.compose.ui.graphics.Color.White
            )
        }
    }
}

@Composable
private fun FavoriteCitiesSection(cities: List<String>, onCitySelected: (String) -> Unit) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = "Favorites",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(modifier = Modifier.padding(top = 8.dp)) {
            items(cities, key = { it }) { city ->
                AssistChip(
                    onClick = { onCitySelected(city) },
                    label = { Text(city) },
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
