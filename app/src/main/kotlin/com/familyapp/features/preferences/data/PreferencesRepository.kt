package com.familyapp.features.preferences.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.familyapp.features.suggestions.models.ActivityConstraints
import com.familyapp.features.suggestions.models.EnergyLevel
import com.familyapp.features.suggestions.models.FoodType
import com.familyapp.features.suggestions.models.WeatherCondition
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "activity_constraints")

@Singleton
class PreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val MAX_WALKING_DISTANCE = intPreferencesKey("max_walking_distance")
        val REQUIRES_BATHROOM = booleanPreferencesKey("requires_bathroom")
        val FOOD_PREFERENCE = stringPreferencesKey("food_preference")
        val WEATHER_PREFERENCE = stringPreferencesKey("weather_preference")
        val ENERGY_LEVEL = stringPreferencesKey("energy_level")
    }

    val constraintsFlow: Flow<ActivityConstraints> = context.dataStore.data.map { prefs ->
        ActivityConstraints(
            maxWalkingDistanceMeters = prefs[Keys.MAX_WALKING_DISTANCE] ?: 500,
            requiresBathroomAccess = prefs[Keys.REQUIRES_BATHROOM] ?: true,
            foodPreference = parseFoodType(prefs[Keys.FOOD_PREFERENCE]),
            weatherPreference = parseWeatherCondition(prefs[Keys.WEATHER_PREFERENCE]),
            energyLevel = parseEnergyLevel(prefs[Keys.ENERGY_LEVEL])
        )
    }

    suspend fun updateMaxWalkingDistance(distance: Int) {
        context.dataStore.edit { prefs -> prefs[Keys.MAX_WALKING_DISTANCE] = distance }
    }

    suspend fun updateBathroomAccess(required: Boolean) {
        context.dataStore.edit { prefs -> prefs[Keys.REQUIRES_BATHROOM] = required }
    }

    suspend fun updateFoodPreference(type: FoodType) {
        context.dataStore.edit { prefs -> prefs[Keys.FOOD_PREFERENCE] = type.name }
    }

    suspend fun updateWeatherPreference(condition: WeatherCondition) {
        context.dataStore.edit { prefs -> prefs[Keys.WEATHER_PREFERENCE] = condition.name }
    }

    suspend fun updateEnergyLevel(level: EnergyLevel) {
        context.dataStore.edit { prefs -> prefs[Keys.ENERGY_LEVEL] = level.name }
    }

    private fun parseFoodType(value: String?): FoodType =
        FoodType.entries.firstOrNull { it.name == value } ?: FoodType.KID_FRIENDLY

    private fun parseWeatherCondition(value: String?): WeatherCondition =
        WeatherCondition.entries.firstOrNull { it.name == value } ?: WeatherCondition.INDOOR_IF_RAIN

    private fun parseEnergyLevel(value: String?): EnergyLevel =
        EnergyLevel.entries.firstOrNull { it.name == value } ?: EnergyLevel.HIGH
}
