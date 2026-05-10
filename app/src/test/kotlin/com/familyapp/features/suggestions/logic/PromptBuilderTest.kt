package com.familyapp.features.suggestions.logic

import android.location.Location
import com.familyapp.features.suggestions.models.ActivityConstraints
import com.familyapp.features.suggestions.models.EnergyLevel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Test

class PromptBuilderTest {

    private val promptBuilder = PromptBuilder()

    @Test
    fun `buildSystemPrompt includes weather description`() {
        val constraints = ActivityConstraints()
        val prompt = promptBuilder.buildSystemPrompt(constraints, null, "Rainy, 55F")
        assertTrue(prompt.contains("Rainy, 55F"))
    }

    @Test
    fun `buildSystemPrompt includes max walking distance`() {
        val constraints = ActivityConstraints(maxWalkingDistanceMeters = 1200)
        val prompt = promptBuilder.buildSystemPrompt(constraints, null, null)
        assertTrue(prompt.contains("1200"))
    }

    @Test
    fun `buildSystemPrompt includes bathroom access requirement`() {
        val constraints = ActivityConstraints(requiresBathroomAccess = true)
        val prompt = promptBuilder.buildSystemPrompt(constraints, null, null)
        assertTrue(prompt.contains("true"))
    }

    @Test
    fun `buildSystemPrompt includes energy level`() {
        val constraints = ActivityConstraints(energyLevel = EnergyLevel.LOW)
        val prompt = promptBuilder.buildSystemPrompt(constraints, null, null)
        assertTrue(prompt.contains("LOW"))
    }

    @Test
    fun `buildSystemPrompt includes location coordinates when provided`() {
        val location = mockk<Location>()
        every { location.latitude } returns 40.7128
        every { location.longitude } returns -74.0060
        val constraints = ActivityConstraints()
        val prompt = promptBuilder.buildSystemPrompt(constraints, location, null)
        assertTrue(prompt.contains("40.7128"))
        assertTrue(prompt.contains("-74.006"))
    }

    @Test
    fun `buildSystemPrompt uses Unknown for null location`() {
        val constraints = ActivityConstraints()
        val prompt = promptBuilder.buildSystemPrompt(constraints, null, null)
        assertTrue(prompt.contains("Unknown"))
    }

    @Test
    fun `buildSystemPrompt mentions children ages`() {
        val constraints = ActivityConstraints()
        val prompt = promptBuilder.buildSystemPrompt(constraints, null, null)
        assertTrue(prompt.contains("5") && prompt.contains("6"))
    }

    @Test
    fun `buildSystemPrompt requests JSON array output`() {
        val constraints = ActivityConstraints()
        val prompt = promptBuilder.buildSystemPrompt(constraints, null, null)
        assertTrue(prompt.contains("JSON"))
    }
}
