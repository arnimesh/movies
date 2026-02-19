package com.example.inshorts.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Response body for GET /movie/now_playing.
 *
 * Same structure as trending: page, results (list of [MovieDto]), total_pages, total_results.
 */
data class NowPlayingResponseDto(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieDto>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
)
