package com.aneesh.weather.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentCityProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun getCity(): String? = withContext(Dispatchers.IO) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return@withContext null
        }
        val locationManager = context.getSystemService(LocationManager::class.java)
        val location = listOf(LocationManager.NETWORK_PROVIDER, LocationManager.PASSIVE_PROVIDER)
            .firstNotNullOfOrNull { provider ->
                runCatching { locationManager.getLastKnownLocation(provider) }.getOrNull()
            } ?: return@withContext null

        @Suppress("DEPRECATION")
        val address = runCatching {
            Geocoder(context, Locale.getDefault()).getFromLocation(location.latitude, location.longitude, 1)
                ?.firstOrNull()
        }.getOrNull()
        address?.locality ?: address?.subAdminArea ?: address?.adminArea
    }
}
