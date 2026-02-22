package com.familyapp.core.llm

import com.familyapp.core.config.LlmProviderConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class CloudClient(private val config: LlmProviderConfig) : LlmClient {

    override suspend fun generateSuggestions(prompt: String): String = withContext(Dispatchers.IO) {
        val endpoint = config.endpoint ?: "https://generativelanguage.googleapis.com/v1beta/models/${config.model}:generateContent"
        val apiKey = config.apiKey ?: throw IllegalArgumentException("API Key required for Cloud Client")
        
        // Gemini API Request Format
        val jsonBody = JSONObject().apply {
            put("contents", JSONArray().put(
                JSONObject().put("parts", JSONArray().put(
                    JSONObject().put("text", prompt)
                ))
            ))
        }

        val url = URL("$endpoint?key=$apiKey")
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
            // Parse Gemini response structure
            val candidates = jsonResponse.getJSONArray("candidates")
            val content = candidates.getJSONObject(0).getJSONObject("content")
            val parts = content.getJSONArray("parts")
            return@withContext parts.getJSONObject(0).getString("text")
        } else {
            val errorStream = connection.errorStream?.bufferedReader()?.use { it.readText() }
            throw Exception("Cloud API Error: $responseCode - $errorStream")
        }
    }
}
