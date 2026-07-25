package com.aneesh.weather.feature.weather.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aneesh.weather.feature.weather.domain.model.Weather
import com.aneesh.weather.feature.weather.presentation.components.CurrentWeatherCard
import com.aneesh.weather.feature.weather.presentation.components.WeatherSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {

        when (val state = uiState) {
            is HomeUiState.Loading -> {
                LoadingScreen()
            }

            is HomeUiState.Error -> {
                ErrorScreen(state.message)
            }

            is HomeUiState.Success -> {
                PullToRefreshBox(
                    isRefreshing = false,
                    onRefresh = {
                        viewModel.onEvent(HomeEvent.Refresh)
                    }
                ) {
                    WeatherContent(
                        weather = state.weather,
                        isOffline = state.isOffline,
                        onSearch = { viewModel.onEvent(HomeEvent.SearchCity(it)) }
                    )
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
    onSearch: (String) -> Unit
) {
    LazyColumn {
        item {
            WeatherSearchBar(initialValue = weather.city, onSearch = onSearch)
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
            CurrentWeatherCard(weather = weather)
        }
        item {
            WeatherDetailsCard(weather)
        }
        item {
            HourlyForecastSection(weather = weather.hourly)
        }
        item {
            DailyForecastSection(weather = weather.daily)
        }
    }
}



