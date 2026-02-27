package com.familyapp.features.suggestions.logic

import com.familyapp.features.suggestions.models.ActivitySuggestion
import org.junit.Assert.assertTrue
import org.junit.Test

class PackingListGeneratorTest {

    private val generator = PackingListGenerator()

    private fun makeActivity(title: String, description: String = "Activity") =
        ActivitySuggestion(title, description, "Great for kids", "", 0.0, 0.0, 60)

    @Test
    fun `generatePackingList always includes water bottles`() {
        val items = generator.generatePackingList(makeActivity("Anything"), "Sunny, 72F")
        assertTrue(items.any { it.contains("Water bottles") })
    }

    @Test
    fun `generatePackingList always includes snacks`() {
        val items = generator.generatePackingList(makeActivity("Anything"), "Sunny, 72F")
        assertTrue(items.any { it.contains("Snacks") })
    }

    @Test
    fun `generatePackingList adds umbrella for rainy weather`() {
        val items = generator.generatePackingList(makeActivity("Walk"), "rainy and cold")
        assertTrue(items.any { it.contains("Umbrellas") })
        assertTrue(items.any { it.contains("Rain jackets") })
    }

    @Test
    fun `generatePackingList adds sunscreen for sunny weather`() {
        val items = generator.generatePackingList(makeActivity("Walk"), "sunny and hot")
        assertTrue(items.any { it.contains("Sunscreen") })
        assertTrue(items.any { it.contains("Hats") })
    }

    @Test
    fun `generatePackingList adds band-aids for park activity`() {
        val activity = makeActivity("Park Visit", "Visit the local playground")
        val items = generator.generatePackingList(activity, "Clear sky, 68F")
        assertTrue(items.any { it.contains("Band-aids") })
        assertTrue(items.any { it.contains("Ball") })
    }

    @Test
    fun `generatePackingList adds towels for swimming activity`() {
        val activity = makeActivity("Pool Day", "Swimming at the local pool")
        val items = generator.generatePackingList(activity, "Sunny")
        assertTrue(items.any { it.contains("Towels") })
        assertTrue(items.any { it.contains("Swimsuits") })
    }

    @Test
    fun `generatePackingList adds bug spray for hiking`() {
        val activity = makeActivity("Nature Hike", "A walk along the trail")
        val items = generator.generatePackingList(activity, "Clear sky")
        assertTrue(items.any { it.contains("Bug spray") })
    }

    @Test
    fun `generatePackingList adds quiet toys for museum`() {
        val activity = makeActivity("Museum Trip", "Visit the children's museum")
        val items = generator.generatePackingList(activity, "Rainy")
        assertTrue(items.any { it.contains("Coloring books") || it.contains("Quiet toys") })
    }
}
