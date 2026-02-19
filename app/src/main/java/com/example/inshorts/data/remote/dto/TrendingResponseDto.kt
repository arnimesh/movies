package com.example.inshorts.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Response body for GET /trending/movie/day (or week).
 *
 * TMDB returns { "page", "results", "total_pages", "total_results" }.
 * We only need [results] to map to domain and store in Room.
 */
data class TrendingResponseDto(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieDto>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
)
