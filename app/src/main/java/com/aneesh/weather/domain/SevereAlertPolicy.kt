package com.aneesh.weather.feature.weather.domain

object SevereAlertPolicy {
    fun shouldNotify(severity: String): Boolean =
        severity.contains("severe", ignoreCase = true) || severity.contains("extreme", ignoreCase = true)
}
