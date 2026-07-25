package com.aneesh.weather.presentation.home

import android.widget.Toast
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aneesh.weather.BuildConfig
import com.aneesh.weather.R
import com.aneesh.weather.domain.model.Weather
import com.aneesh.weather.presentation.components.DailyForecastSection
import com.aneesh.weather.presentation.components.HourlyForecastSection
import com.aneesh.weather.presentation.components.WeatherDetailsCard
import com.aneesh.weather.presentation.components.WeatherHero
import com.aneesh.weather.presentation.components.WeatherSearchBar
import com.aneesh.weather.presentation.theme.LocalWeatherPalette
import com.aneesh.weather.presentation.theme.SetStatusBarColor
import com.aneesh.weather.presentation.theme.toWeatherPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteCities by viewModel.favoriteCities.collectAsStateWithLifecycle()
    val citySuggestions by viewModel.citySuggestions.collectAsStateWithLifecycle()
    val isSearchingCities by viewModel.isSearchingCities.collectAsStateWithLifecycle()
    val needsInitialLocation by viewModel.needsInitialLocation.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        viewModel.onEvent(if (granted) HomeEvent.LoadCurrentCity else HomeEvent.UseFallbackCity)
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is HomeEffect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(needsInitialLocation) {
        if (needsInitialLocation) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                viewModel.onEvent(HomeEvent.LoadCurrentCity)
            } else {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        when (val state = uiState) {
            is HomeUiState.Loading -> {
                LoadingScreen()
            }

            is HomeUiState.Error -> {
                ErrorScreen(state.message)
            }

            is HomeUiState.Success -> {
                val palette = state.weather.condition.toWeatherPalette()
                CompositionLocalProvider(LocalWeatherPalette provides palette) {
                    SetStatusBarColor(palette.background.first())
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(palette.background))
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
                                citySuggestions = citySuggestions,
                                isSearchingCities = isSearchingCities,
                                onSearchQueryChanged = {
                                    viewModel.onEvent(HomeEvent.SearchQueryChanged(it))
                                },
                                onSuggestionSelected = {
                                    viewModel.onEvent(HomeEvent.SelectCitySuggestion(it))
                                },
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
    citySuggestions: List<com.aneesh.weather.domain.model.CitySuggestion>,
    isSearchingCities: Boolean,
    onSearchQueryChanged: (String) -> Unit,
    onSuggestionSelected: (com.aneesh.weather.domain.model.CitySuggestion) -> Unit,
    onTestAlert: () -> Unit,
    favoriteCities: List<String>,
    onFavoriteSelected: (String) -> Unit,
    onToggleFavorite: () -> Unit
) {
    val palette = LocalWeatherPalette.current
    LazyColumn {
        item {
            WeatherSearchBar(
                initialValue = weather.city,
                suggestions = citySuggestions,
                isSearching = isSearchingCities,
                onQueryChanged = onSearchQueryChanged,
                onSuggestionSelected = onSuggestionSelected,
                onSearch = onSearch
            )
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
                onToggleFavorite = onToggleFavorite
            )
        }
        item {
            WeatherDetailsCard(weather)
        }
        if (BuildConfig.DEBUG) {
            item {
                Button(
                    onClick = onTestAlert,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = palette.cardContainer,
                        contentColor = palette.content
                    )
                ) { Text(stringResource(R.string.send_test_severe_weather_alert)) }
            }
        }
        item {
            HourlyForecastSection(weather.hourly)
        }
        item {
            DailyForecastSection(weather.daily)
        }
    }
}

@Composable
private fun FavoriteCitiesSection(cities: List<String>, onCitySelected: (String) -> Unit) {
    val palette = LocalWeatherPalette.current
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = stringResource(R.string.favorites),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = palette.content
        )
        LazyRow(modifier = Modifier.padding(top = 8.dp)) {
            items(cities, key = { it }) { city ->
                AssistChip(
                    onClick = { onCitySelected(city) },
                    label = { Text(city) },
                    modifier = Modifier.padding(start = 16.dp),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = palette.cardContainer,
                        labelColor = palette.content
                    ),
                    border = AssistChipDefaults.assistChipBorder(
                        enabled = true,
                        borderColor = palette.mutedContent
                    )
                )
            }
        }
    }
}
