package com.aneesh.weather.feature.weather.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aneesh.weather.feature.weather.domain.repository.WeatherRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WeatherSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val repository: WeatherRepository,
    private val notifier: WeatherAlertNotifier
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val result = repository.syncCachedCities()
        result.severeAlerts.forEach(notifier::notify)
        return if (result.completed) Result.success() else Result.retry()
    }
}
