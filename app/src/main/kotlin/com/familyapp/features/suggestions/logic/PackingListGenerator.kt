package com.familyapp.features.suggestions.logic

import com.familyapp.features.suggestions.models.ActivitySuggestion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PackingListGenerator @Inject constructor() {

    fun generatePackingList(activity: ActivitySuggestion, weatherDescription: String): List<String> {
        val items = mutableListOf<String>()
        val lowerWeather = weatherDescription.lowercase()
        val lowerActivity = activity.description.lowercase() + " " + activity.title.lowercase()
        
        // Always pack
        items.add("Water bottles")
        items.add("Snacks (Granola bars/Fruit)")
        items.add("Hand sanitizer")
        
        // Weather Logic
        if (lowerWeather.contains("rain") || lowerWeather.contains("drizzle")) {
            items.add("Umbrellas")
            items.add("Rain jackets")
        } else if (lowerWeather.contains("sunny") || lowerWeather.contains("hot")) {
            items.add("Sunscreen")
            items.add("Hats")
        }
        
        // Activity Logic
        if (lowerActivity.contains("park") || lowerActivity.contains("playground")) {
            items.add("Band-aids")
            items.add("Ball/Frisbee")
        }
        if (lowerActivity.contains("hike") || lowerActivity.contains("trail")) {
            items.add("Bug spray")
            items.add("Extra socks")
        }
        if (lowerActivity.contains("swim") || lowerActivity.contains("pool") || lowerActivity.contains("beach")) {
            items.add("Towels")
            items.add("Swimsuits")
            items.add("Wet bag")
        }
        if (lowerActivity.contains("museum") || lowerActivity.contains("library")) {
            items.add("Quiet toys/Coloring books")
        }
        
        return items
    }
}
