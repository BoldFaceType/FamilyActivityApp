package com.familyapp.features.suggestions

import com.familyapp.features.preferences.data.PreferencesRepository
import com.familyapp.features.suggestions.data.SuggestionsRepository
import com.familyapp.features.suggestions.models.ActivityConstraints
import com.familyapp.features.suggestions.models.ActivitySuggestion
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SuggestionsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val suggestionsRepo = mockk<SuggestionsRepository>()
    private val preferencesRepo = mockk<PreferencesRepository>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun makeViewModel() = SuggestionsViewModel(suggestionsRepo, preferencesRepo)

    @Test
    fun `initial state is Idle`() {
        val vm = makeViewModel()
        assertTrue(vm.uiState.value is SuggestionsUiState.Idle)
    }

    @Test
    fun `generateSuggestions transitions Idle to Loading to Success`() = runTest {
        val suggestions = listOf(
            ActivitySuggestion("Park", "Nice park", "Kid-friendly", "Central Park", 40.7, -74.0, 60)
        )
        coEvery { preferencesRepo.constraintsFlow } returns flowOf(ActivityConstraints())
        coEvery { suggestionsRepo.generateSuggestions(any(), any()) } returns Result.success(suggestions)

        val vm = makeViewModel()
        val states = mutableListOf<SuggestionsUiState>()
        val collectJob = launch { vm.uiState.collect { states.add(it) } }

        vm.generateSuggestions()
        advanceUntilIdle()
        collectJob.cancel()

        assertTrue(states.any { it is SuggestionsUiState.Loading })
        assertTrue(states.last() is SuggestionsUiState.Success)
        val success = states.last() as SuggestionsUiState.Success
        assertTrue(success.suggestions.isNotEmpty())
    }

    @Test
    fun `generateSuggestions transitions to Error on repository failure`() = runTest {
        coEvery { preferencesRepo.constraintsFlow } returns flowOf(ActivityConstraints())
        coEvery { suggestionsRepo.generateSuggestions(any(), any()) } returns Result.failure(Exception("API error"))

        val vm = makeViewModel()
        val states = mutableListOf<SuggestionsUiState>()
        val collectJob = launch { vm.uiState.collect { states.add(it) } }

        vm.generateSuggestions()
        advanceUntilIdle()
        collectJob.cancel()

        assertTrue(states.last() is SuggestionsUiState.Error)
        val error = states.last() as SuggestionsUiState.Error
        assertTrue(error.message.contains("API error"))
    }
}
