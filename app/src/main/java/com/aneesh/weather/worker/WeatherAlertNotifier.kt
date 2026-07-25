package com.aneesh.weather.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.aneesh.weather.R
import com.aneesh.weather.domain.model.SevereWeatherAlert
import com.aneesh.weather.presentation.home.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.java

@Singleton
class WeatherAlertNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun notify(alert: SevereWeatherAlert) {
        createChannel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) return

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            alert.city.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.weather_launcher)
            .setContentTitle("${alert.event} in ${alert.city}")
            .setContentText(alert.headline)
            .setStyle(NotificationCompat.BigTextStyle().bigText(alert.description.ifBlank { alert.headline }))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(alert.city.plus(alert.event).hashCode(), notification)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Severe weather alerts",
            NotificationManager.IMPORTANCE_HIGH
        ).apply { description = "Important weather warnings for saved cities" }
        context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    companion object { const val CHANNEL_ID = "severe_weather_alerts" }
}
