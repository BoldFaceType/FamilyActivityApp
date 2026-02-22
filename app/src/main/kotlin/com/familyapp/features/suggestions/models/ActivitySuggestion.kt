package com.familyapp.features.suggestions.models

data class ActivitySuggestion(
    val title: String,
    val description: String,
    val reasonForFit: String,
    val locationName: String,
    val lat: Double,
    val lng: Double,
    val estimatedDurationMinutes: Int
)
