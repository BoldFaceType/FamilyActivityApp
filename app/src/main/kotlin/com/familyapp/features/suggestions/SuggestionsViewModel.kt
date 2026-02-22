package com.familyapp.features.suggestions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.familyapp.features.preferences.data.PreferencesRepository
import com.familyapp.features.suggestions.data.SuggestionsRepository
import com.familyapp.features.suggestions.models.ActivitySuggestion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SuggestionsUiState {
    object Idle : SuggestionsUiState()
    object Loading : SuggestionsUiState()
    data class Success(val suggestions: List<ActivitySuggestion>) : SuggestionsUiState()
    data class Error(val message: String) : SuggestionsUiState()
}

@HiltViewModel
class SuggestionsViewModel @Inject constructor(
    private val suggestionsRepository: SuggestionsRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SuggestionsUiState>(SuggestionsUiState.Idle)
    val uiState: StateFlow<SuggestionsUiState> = _uiState.asStateFlow()

    fun generateSuggestions() {
        viewModelScope.launch {
            _uiState.value = SuggestionsUiState.Loading
            
            // Get current constraints from preferences
            val constraints = preferencesRepository.constraintsFlow.first()
            
            // TODO: Integrate real weather service. For now, hardcode or prompt user.
            val weather = "Sunny, 72F" 
            
            suggestionsRepository.generateSuggestions(constraints, weather)
                .onSuccess { suggestions ->
                    _uiState.value = SuggestionsUiState.Success(suggestions)
                }
                .onFailure { error ->
                    _uiState.value = SuggestionsUiState.Error(error.message ?: "Unknown error")
                }
        }
    }
}
