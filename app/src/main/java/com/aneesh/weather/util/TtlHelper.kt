package com.aneesh.weather.util

import com.aneesh.weather.feature.weather.data.db.WeatherEntity

fun WeatherEntity?.needsRefresh(): Boolean {

    if (this == null)
        return true

    return System.currentTimeMillis() - updatedAt >
            CachePolicy.WEATHER_CACHE_TTL

}