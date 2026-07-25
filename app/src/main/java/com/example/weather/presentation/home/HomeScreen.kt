package com.example.weather.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather.domain.model.DailyWeather
import com.example.weather.domain.model.HourlyWeather
import com.example.weather.domain.model.Weather

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar()

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
                    WeatherContent(state.weather)
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
private fun WeatherContent(weather: Weather) {
    LazyColumn {
        item {
            SearchBar()
        }
        item {
            CurrentWeatherCard(weather = weather)
        }
        item {
            WeatherDetailsCard()
        }
        item {
            HourlyForecast(weather = weather)
        }
        item {
            TemperatureChart()
        }
        item {
            DailyForecast(weather = weather)
        }

    }
}

@Composable
fun TemperatureChart() {
    // Implement Temperature Chart
}

@Composable
fun WeatherDetailsCard() {
    // Implement WeatherDetailsCard
}

@Composable
private fun SearchBar() {
    // Implement SearchBar
}

@Composable
private fun CurrentWeatherCard(weather: Weather) {
    Card {
        Column {
            Text(weather.city)
            Text("${weather.temperature}°C")
            Text(weather.condition)
        }
    }
}

@Composable
private fun WeatherDetails(weather: Weather) {
    // Implement WeatherDetails
}

@Composable
private fun HourlyForecast(weather: Weather) {
//    LazyRow {
//        items(weather.hourly) { hour ->
//            HourItem(hour)
//        }
//    }
}

@Composable
private fun HourItem(hour: HourlyWeather) {
    // Implement HourItem
}

@Composable
private fun DailyForecast(weather: Weather) {
//    LazyColumn {
//        items(weather.daily) { day ->
//            DailyItem(day)
//        }
//    }
}

@Composable
private fun DailyItem(day: DailyWeather) {
    // Implement DailyItem
}
