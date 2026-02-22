package com.familyapp.features.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.familyapp.features.preferences.data.PreferencesRepository
import com.familyapp.features.suggestions.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val repository: PreferencesRepository
) : ViewModel() {

    val uiState: StateFlow<ActivityConstraints> = repository.constraintsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ActivityConstraints()
        )

    fun updateMaxWalkingDistance(distance: Int) {
        viewModelScope.launch {
            repository.updateMaxWalkingDistance(distance)
        }
    }

    fun updateBathroomAccess(required: Boolean) {
        viewModelScope.launch {
            repository.updateBathroomAccess(required)
        }
    }

    fun updateFoodPreference(type: FoodType) {
        viewModelScope.launch {
            repository.updateFoodPreference(type)
        }
    }

    fun updateWeatherPreference(condition: WeatherCondition) {
        viewModelScope.launch {
            repository.updateWeatherPreference(condition)
        }
    }

    fun updateEnergyLevel(level: EnergyLevel) {
        viewModelScope.launch {
            repository.updateEnergyLevel(level)
        }
    }
}
