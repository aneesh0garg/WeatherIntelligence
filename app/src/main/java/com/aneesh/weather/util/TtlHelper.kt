package com.aneesh.weather.util

import com.aneesh.weather.feature.weather.data.db.WeatherEntity

fun WeatherEntity?.needsRefresh(): Boolean {
    return this == null || CachePolicy.isStale(updatedAt)

}
