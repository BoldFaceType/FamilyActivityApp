package com.familyapp.features.map_integration

import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesService {
    @GET("place/textsearch/json")
    suspend fun searchPlaces(
        @Query("query") query: String,
        @Query("key") apiKey: String
    ): PlacesResponse
}

data class PlacesResponse(
    val results: List<PlaceResult>
)

data class PlaceResult(
    val name: String,
    val formatted_address: String,
    val geometry: Geometry
)

data class Geometry(
    val location: LatLngLiteral
)

data class LatLngLiteral(
    val lat: Double,
    val lng: Double
)

@Singleton
class PlacesClient @Inject constructor(
    private val service: PlacesService
) {
    suspend fun search(query: String, apiKey: String): List<PlaceResult> {
        return service.searchPlaces(query, apiKey).results
    }
}
