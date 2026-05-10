package com.familyapp.features.suggestions.models

data class WeatherData(
    val temperatureF: Double,
    val conditionLabel: String
) {
    override fun toString(): String = "$conditionLabel, ${temperatureF.toInt()}°F"
}
