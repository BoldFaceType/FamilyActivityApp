package com.familyapp.features.preferences.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.familyapp.features.preferences.PreferencesViewModel
import com.familyapp.features.suggestions.models.*

@Composable
fun PreferencesScreen(
    viewModel: PreferencesViewModel = hiltViewModel(),
    onNavigateToSuggestions: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    PreferencesContent(
        constraints = state,
        onDistanceChange = viewModel::updateMaxWalkingDistance,
        onBathroomChange = viewModel::updateBathroomAccess,
        onFoodChange = viewModel::updateFoodPreference,
        onWeatherChange = viewModel::updateWeatherPreference,
        onEnergyChange = viewModel::updateEnergyLevel
    )
}

@Composable
fun PreferencesContent(
    constraints: ActivityConstraints,
    onDistanceChange: (Int) -> Unit,
    onBathroomChange: (Boolean) -> Unit,
    onFoodChange: (FoodType) -> Unit,
    onWeatherChange: (WeatherCondition) -> Unit,
    onEnergyChange: (EnergyLevel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Family Preferences", style = MaterialTheme.typography.headlineMedium)

        // Bathroom Toggle
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Requires Bathroom Access", modifier = Modifier.weight(1f))
            Switch(
                checked = constraints.requiresBathroomAccess,
                onCheckedChange = onBathroomChange
            )
        }

        HorizontalDivider()

        // Walking Distance Slider
        Text("Max Walking Distance: ${constraints.maxWalkingDistanceMeters}m")
        Slider(
            value = constraints.maxWalkingDistanceMeters.toFloat(),
            onValueChange = { onDistanceChange(it.toInt()) },
            valueRange = 100f..5000f,
            steps = 49
        )

        HorizontalDivider()

        // Energy Level (Chips)
        Text("Energy Level")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EnergyLevel.values().forEach { level ->
                FilterChip(
                    selected = constraints.energyLevel == level,
                    onClick = { onEnergyChange(level) },
                    label = { Text(level.name) }
                )
            }
        }
        
        HorizontalDivider()
        
        // Weather Preference
        Text("Weather Preference")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
             WeatherCondition.values().forEach { weather ->
                FilterChip(
                    selected = constraints.weatherPreference == weather,
                    onClick = { onWeatherChange(weather) },
                    label = { Text(weather.name) }
                )
            }
        }

        HorizontalDivider()

        // Food Preference
        Text("Food Preference")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
             FoodType.values().forEach { food ->
                FilterChip(
                    selected = constraints.foodPreference == food,
                    onClick = { onFoodChange(food) },
                    label = { Text(food.name) }
                )
            }
        }
    }
}
