package com.example.weather.util

import com.example.weather.data.db.WeatherEntity

fun WeatherEntity?.needsRefresh(): Boolean {

    if (this == null)
        return true

    return System.currentTimeMillis() - updatedAt >
            CachePolicy.WEATHER_CACHE_TTL

}