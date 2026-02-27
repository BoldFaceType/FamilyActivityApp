package com.familyapp.features.suggestions.data

import com.familyapp.features.suggestions.models.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor() {

    suspend fun getWeather(lat: Double, lng: Double): WeatherData = withContext(Dispatchers.IO) {
        val url = URL("https://api.open-meteo.com/v1/forecast?latitude=$lat&longitude=$lng&current_weather=true")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 5000
        connection.readTimeout = 5000

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val json = JSONObject(response)
            val currentWeather = json.getJSONObject("current_weather")
            val tempCelsius = currentWeather.getDouble("temperature")
            val wmoCode = currentWeather.getInt("weathercode")
            WeatherData(
                temperatureF = celsiusToFahrenheit(tempCelsius),
                conditionLabel = wmoCodeToLabel(wmoCode)
            )
        } else {
            throw Exception("Weather API error: $responseCode")
        }
    }

    private fun celsiusToFahrenheit(celsius: Double): Double = (celsius * 9.0 / 5.0) + 32

    private fun wmoCodeToLabel(code: Int): String = when (code) {
        0 -> "Clear sky"
        1, 2, 3 -> "Partly cloudy"
        45, 48 -> "Foggy"
        51, 53, 55 -> "Drizzle"
        61, 63, 65 -> "Rain"
        71, 73, 75 -> "Snow"
        80, 81, 82 -> "Rain showers"
        95 -> "Thunderstorm"
        else -> "Cloudy"
    }
}
