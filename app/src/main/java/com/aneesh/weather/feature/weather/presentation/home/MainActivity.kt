package com.aneesh.weather.feature.weather.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aneesh.weather.feature.weather.presentation.navigation.WeatherNavGraph
import com.aneesh.weather.feature.weather.presentation.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                WeatherNavGraph()
            }
        }
    }
}