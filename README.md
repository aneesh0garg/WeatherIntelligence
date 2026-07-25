# Weather Intelligence

An offline-first Android weather forecast app built for the Standard Chartered assessment. It shows current conditions, hourly weather, and a seven-day forecast using [WeatherAPI.com](https://www.weatherapi.com/docs/).

## Architecture

The app follows a clean, feature-oriented MVVM structure:

```
Compose UI → HomeViewModel → GetWeatherUseCase → WeatherRepository
                                                   ├─ Room (source of truth)
                                                   └─ Retrofit (WeatherAPI)
```

- **Presentation:** Jetpack Compose, `StateFlow`, lifecycle-aware collection.
- **Domain:** `Weather` models, repository contract, and use case.
- **Data:** Retrofit API client, Room entity/DAO, mapping layer, and repository implementation.
- **Dependency injection:** Hilt.

## Offline-first and smart refresh

Room is the app's source of truth. A city forecast is cached with an `updatedAt` timestamp. On a request, the repository refreshes only when the 30-minute TTL has expired (or the user pulls to refresh). If a refresh fails and a cached response exists, the cached forecast is displayed with an offline banner. A first-load network failure is shown as an error state.

`WeatherSyncWorker` schedules a unique six-hour WorkManager job with a network constraint. It refreshes every city already stored in Room, so background work neither needs a hard-coded city nor fetches data when the app has no saved weather data yet. WeatherAPI alerts marked **Severe** or **Extreme** are posted as high-priority notifications. Android 13+ requests notification permission when the app first opens.

## Testing notifications

1. Install a **debug** build and accept the notification permission prompt.
2. Load any city, then tap **Send test severe-weather alert**. This button is compiled only into debug builds.
3. For a real background path, leave at least one city cached, force-run the `weather_periodic_sync` job with Android Studio's Background Task Inspector, and use a city currently covered by a WeatherAPI severe/extreme alert.

If a notification does not appear, confirm that the app's **Severe weather alerts** channel is enabled in Android system notification settings.

## Setup

1. Create a free API key at [WeatherAPI.com](https://www.weatherapi.com/signup.aspx).
2. Add the key to `local.properties` (this file is not committed):

   ```properties
   WEATHER_API_KEY=your_key_here
   ```

3. Open the project in Android Studio, sync Gradle, and run the `app` configuration on an Android 7.0+ device/emulator.

Or run the checks from the repository root:

```bash
./gradlew :app:testDebugUnitTest
./gradlew :app:assembleDebug
```

The generated APK is located at `app/build/outputs/apk/debug/app-debug.apk`.

## Assumptions and trade-offs

- City search is text based and WeatherAPI resolves the city name.
- Forecast units follow WeatherAPI's default metric response.
- Cache TTL is intentionally conservative (30 minutes) to balance freshness and API usage.
- API keys are injected through `local.properties` or the `WEATHER_API_KEY` Gradle property and are never hard-coded.
- Severe notifications are limited to WeatherAPI alerts whose severity is `Severe` or `Extreme`; notification delivery is subject to Android system-level notification settings.

## Tests

`CachePolicyTest` covers the TTL boundary and expired-cache behavior. The policy is a pure function to make it deterministic and easy to extend with repository and ViewModel tests.
