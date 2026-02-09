package com.familyapp.features.suggestions.models

data class ActivityConstraints(
    val maxWalkingDistanceMeters: Int = 500,
    val requiresBathroomAccess: Boolean = true,
    val foodPreference: FoodType = FoodType.KID_FRIENDLY,
    val weatherPreference: WeatherCondition = WeatherCondition.INDOOR_IF_RAIN,
    val energyLevel: EnergyLevel = EnergyLevel.HIGH
)

enum class FoodType { KID_FRIENDLY, PACKED_LUNCH, ANY }
enum class WeatherCondition { INDOOR_ONLY, OUTDOOR_ONLY, INDOOR_IF_RAIN }
enum class EnergyLevel { LOW, MEDIUM, HIGH }
