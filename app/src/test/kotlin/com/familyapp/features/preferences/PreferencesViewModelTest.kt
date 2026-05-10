package com.familyapp.features.preferences

import com.familyapp.features.preferences.data.PreferencesRepository
import com.familyapp.features.suggestions.models.ActivityConstraints
import com.familyapp.features.suggestions.models.EnergyLevel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PreferencesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository = mockk<PreferencesRepository>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial uiState value is default ActivityConstraints`() {
        every { repository.constraintsFlow } returns flowOf(ActivityConstraints())
        val vm = PreferencesViewModel(repository)
        // stateIn initialValue is ActivityConstraints() — asserted before flow collects
        assertEquals(ActivityConstraints(), vm.uiState.value)
    }

    @Test
    fun `uiState initial value uses default walking distance`() {
        every { repository.constraintsFlow } returns flowOf(ActivityConstraints())
        val vm = PreferencesViewModel(repository)
        assertEquals(500, vm.uiState.value.maxWalkingDistanceMeters)
    }

    @Test
    fun `uiState initial value requires bathroom access by default`() {
        every { repository.constraintsFlow } returns flowOf(ActivityConstraints())
        val vm = PreferencesViewModel(repository)
        assertEquals(true, vm.uiState.value.requiresBathroomAccess)
    }

    @Test
    fun `uiState initial energy level is HIGH`() {
        every { repository.constraintsFlow } returns flowOf(ActivityConstraints())
        val vm = PreferencesViewModel(repository)
        assertEquals(EnergyLevel.HIGH, vm.uiState.value.energyLevel)
    }
}
