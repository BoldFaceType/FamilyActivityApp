package com.familyapp.features.suggestions.data

import com.familyapp.core.llm.LlmFactory
import com.familyapp.features.map_integration.LocationService
import com.familyapp.features.suggestions.logic.PromptBuilder
import com.familyapp.features.suggestions.models.ActivityConstraints
import com.familyapp.features.suggestions.models.ActivitySuggestion
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException

@Singleton
class SuggestionsRepository @Inject constructor(
    private val locationService: LocationService,
    private val promptBuilder: PromptBuilder,
    private val llmFactory: LlmFactory
) {

    suspend fun generateSuggestions(
        constraints: ActivityConstraints,
        weatherDescription: String?
    ): Result<List<ActivitySuggestion>> = withContext(Dispatchers.IO) {
        try {
            val location = locationService.getCurrentLocation()
            val prompt = promptBuilder.buildSystemPrompt(constraints, location, weatherDescription)
            val client = llmFactory.getClient()
            val llmResponse = client.generateSuggestions(prompt)
            val suggestions = parseSuggestions(llmResponse)
            Result.success(suggestions)
        } catch (networkFailure: IOException) {
            Result.failure(networkFailure)
        } catch (badProviderConfig: IllegalArgumentException) {
            Result.failure(badProviderConfig)
        } catch (badProviderState: IllegalStateException) {
            Result.failure(badProviderState)
        } catch (badResponse: JSONException) {
            Result.failure(badResponse)
        }
    }

    private fun parseSuggestions(jsonString: String): List<ActivitySuggestion> {
        val jsonArray = parseSuggestionArray(jsonString)
        val list = mutableListOf<ActivitySuggestion>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            list.add(
                ActivitySuggestion(
                    title = obj.getString("title"),
                    description = obj.getString("description"),
                    reasonForFit = obj.getString("reason_for_fit"),
                    locationName = obj.optString("location_name", ""),
                    lat = obj.optDouble("lat", 0.0),
                    lng = obj.optDouble("lng", 0.0),
                    estimatedDurationMinutes = obj.optInt("estimated_duration_minutes", 60)
                )
            )
        }
        return list
    }

    private fun parseSuggestionArray(jsonString: String): JSONArray {
        return try {
            JSONArray(jsonString)
        } catch (parseFailure: JSONException) {
            val start = jsonString.indexOf("[")
            val end = jsonString.lastIndexOf("]")
            if (start >= 0 && end > start) {
                JSONArray(jsonString.substring(start, end + 1))
            } else {
                throw parseFailure
            }
        }
    }
}
