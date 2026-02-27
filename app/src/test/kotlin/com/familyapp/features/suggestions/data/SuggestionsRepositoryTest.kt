package com.familyapp.features.suggestions.data

import com.familyapp.core.llm.LlmClient
import com.familyapp.core.llm.LlmFactory
import com.familyapp.features.map_integration.LocationService
import com.familyapp.features.suggestions.logic.PromptBuilder
import com.familyapp.features.suggestions.models.ActivityConstraints
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SuggestionsRepositoryTest {

    private val locationService = mockk<LocationService>()
    private val promptBuilder = mockk<PromptBuilder>()
    private val llmFactory = mockk<LlmFactory>()
    private val llmClient = mockk<LlmClient>()

    private fun makeRepo() = SuggestionsRepository(locationService, promptBuilder, llmFactory)

    private fun setupCommonMocks() {
        coEvery { locationService.getCurrentLocation() } returns null
        every { promptBuilder.buildSystemPrompt(any(), any(), any()) } returns "test prompt"
        every { llmFactory.getClient() } returns llmClient
    }

    @Test
    fun `generateSuggestions parses valid JSON array`() = runTest {
        val validJson = """[{"title":"Park","description":"A nice park","reason_for_fit":"Great for kids","location_name":"Central Park","lat":40.7,"lng":-74.0,"estimated_duration_minutes":60}]"""
        setupCommonMocks()
        coEvery { llmClient.generateSuggestions(any()) } returns validJson

        val result = makeRepo().generateSuggestions(ActivityConstraints(), "Sunny")

        assertTrue(result.isSuccess)
        val suggestions = result.getOrNull()!!
        assertEquals(1, suggestions.size)
        assertEquals("Park", suggestions[0].title)
        assertEquals(60, suggestions[0].estimatedDurationMinutes)
    }

    @Test
    fun `generateSuggestions parses JSON array wrapped in extra text`() = runTest {
        val wrappedJson = """Here are my suggestions: [{"title":"Museum","description":"Interactive museum","reason_for_fit":"Educational","location_name":"City Museum","lat":40.0,"lng":-73.0,"estimated_duration_minutes":90}] Hope you enjoy!"""
        setupCommonMocks()
        coEvery { llmClient.generateSuggestions(any()) } returns wrappedJson

        val result = makeRepo().generateSuggestions(ActivityConstraints(), "Cloudy")

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!.isNotEmpty())
        assertEquals("Museum", result.getOrNull()!![0].title)
    }

    @Test
    fun `generateSuggestions returns failure for completely invalid JSON`() = runTest {
        setupCommonMocks()
        coEvery { llmClient.generateSuggestions(any()) } returns "This is not JSON at all"

        val result = makeRepo().generateSuggestions(ActivityConstraints(), "Sunny")

        assertTrue(result.isFailure)
    }

    @Test
    fun `generateSuggestions returns failure when LLM throws`() = runTest {
        setupCommonMocks()
        coEvery { llmClient.generateSuggestions(any()) } throws Exception("Network error")

        val result = makeRepo().generateSuggestions(ActivityConstraints(), "Sunny")

        assertTrue(result.isFailure)
    }
}
