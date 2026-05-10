package com.familyapp.core.llm

import com.familyapp.core.config.AppConfig
import com.familyapp.core.config.LlmProviderConfig

interface LlmClient {
    suspend fun generateSuggestions(prompt: String): String
}

class LlmFactory(private val config: AppConfig) {
    fun getClient(): LlmClient {
        val providerConfig = config.providers[config.activeLlmProvider] 
            ?: throw IllegalArgumentException("Provider ${config.activeLlmProvider} not found in config")

        return when (config.activeLlmProvider) {
            "local" -> OllamaClient(providerConfig)
            "cloud" -> CloudClient(providerConfig)
            else -> throw IllegalArgumentException("Unknown provider type: ${config.activeLlmProvider}")
        }
    }
}
