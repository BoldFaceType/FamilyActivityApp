package com.familyapp.core.llm

interface LlmClient {
    suspend fun generateSuggestions(prompt: String): String
}

class LlmFactory(private val config: AppConfig) {
    fun getClient(): LlmClient {
        return when (config.activeLlmProvider) {
            "local" -> OllamaClient(config.providers.local)
            "cloud" -> GeminiClient(config.providers.cloud)
            else -> throw IllegalArgumentException("Unknown provider")
        }
    }
}
