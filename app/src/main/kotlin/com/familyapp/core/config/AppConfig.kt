package com.familyapp.core.config

import com.google.gson.annotations.SerializedName

data class AppConfig(
    @SerializedName("active_llm_provider") val activeLlmProvider: String,
    @SerializedName("providers") val providers: Map<String, LlmProviderConfig>,
    @SerializedName("family_defaults") val familyDefaults: FamilyDefaults
)

data class LlmProviderConfig(
    @SerializedName("type") val type: String,
    @SerializedName("endpoint") val endpoint: String? = null,
    @SerializedName("model") val model: String? = null,
    @SerializedName("api_key") val apiKey: String? = null
)

data class FamilyDefaults(
    @SerializedName("adults") val adults: Int,
    @SerializedName("children") val children: Int,
    @SerializedName("child_ages") val childAges: List<Int>,
    @SerializedName("location") val location: String
)
