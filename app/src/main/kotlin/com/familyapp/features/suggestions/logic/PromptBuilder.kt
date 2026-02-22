package com.familyapp.features.suggestions.logic

import android.location.Location
import com.familyapp.features.suggestions.models.ActivityConstraints
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromptBuilder @Inject constructor() {

    fun buildSystemPrompt(
        constraints: ActivityConstraints,
        location: Location?,
        weatherDescription: String?
    ): String {
        val locationStr = location?.let { "${it.latitude}, ${it.longitude}" } ?: "Unknown"
        val weatherStr = weatherDescription ?: "Not available"

        return """
            You are an expert family activity planner for a family with two children (ages 5 and 6).
            Your goal is to suggest 3-5 specific, actionable activities based on the current context and constraints.
            
            Context:
            - Current Location: $locationStr
            - Current Weather: $weatherStr
            
            Constraints:
            - Max Walking Distance: ${constraints.maxWalkingDistanceMeters} meters
            - Requires Bathroom Access: ${constraints.requiresBathroomAccess}
            - Food Preference: ${constraints.foodPreference}
            - Energy Level: ${constraints.energyLevel} (Low=Chill, High=Active)
            - Weather Strategy: ${constraints.weatherPreference}

            Instructions:
            1. Verify each suggestion against the max walking distance if location is provided.
            2. Prioritize activities with verified bathroom access if required.
            3. ensuring the activity is appropriate for 5-6 year olds.
            
            Output Format:
            Return ONLY a valid JSON array of objects. Do not include markdown formatting or explanations outside the JSON.
            JSON Schema:
            [
              {
                "title": "Activity Name",
                "description": "Short description of the activity.",
                "reason_for_fit": "Why this is good for 5-6yo kids and matches constraints.",
                "location_name": "Specific place name",
                "lat": 0.0,
                "lng": 0.0,
                "estimated_duration_minutes": 60
              }
            ]
        """.trimIndent()
    }
}
