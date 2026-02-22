package com.familyapp.features.suggestions.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.familyapp.features.suggestions.SuggestionsUiState
import com.familyapp.features.suggestions.SuggestionsViewModel
import com.familyapp.features.suggestions.models.ActivitySuggestion

@Composable
fun SuggestionsScreen(
    viewModel: SuggestionsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = viewModel::generateSuggestions) {
            Text("Find Activities!")
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        when (val uiState = state) {
            is SuggestionsUiState.Idle -> Text("Tap button to get suggestions.")
            is SuggestionsUiState.Loading -> CircularProgressIndicator()
            is SuggestionsUiState.Error -> Text("Error: ${uiState.message}")
            is SuggestionsUiState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(uiState.suggestions) { suggestion ->
                        ActivityCard(suggestion)
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityCard(suggestion: ActivitySuggestion) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = suggestion.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = suggestion.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Why: ${suggestion.reasonForFit}", style = MaterialTheme.typography.bodySmall)
            
            if (suggestion.locationName.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "📍 ${suggestion.locationName}", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
