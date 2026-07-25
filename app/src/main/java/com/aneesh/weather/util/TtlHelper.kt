package com.aneesh.weather.util

import com.aneesh.weather.data.db.WeatherEntity

fun WeatherEntity?.needsRefresh(): Boolean {
    return this == null || CachePolicy.isStale(updatedAt)

}
