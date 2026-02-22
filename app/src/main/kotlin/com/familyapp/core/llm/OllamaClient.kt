package com.familyapp.core.llm

import com.familyapp.core.config.LlmProviderConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class OllamaClient(private val config: LlmProviderConfig) : LlmClient {

    override suspend fun generateSuggestions(prompt: String): String = withContext(Dispatchers.IO) {
        val endpoint = config.endpoint ?: "http://localhost:11434/api/generate"
        val model = config.model ?: "llama3"

        val jsonBody = JSONObject().apply {
            put("model", model)
            put("prompt", prompt)
            put("stream", false)
        }

        val url = URL(endpoint)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/json")

        OutputStreamWriter(connection.outputStream).use { writer ->
            writer.write(jsonBody.toString())
        }

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonResponse = JSONObject(response)
            // Ollama returns "response" field in the JSON object
            return@withContext jsonResponse.getString("response")
        } else {
            throw Exception("Ollama API Error: $responseCode")
        }
    }
}
