package com.example.inshorts.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO for a movie as returned by TMDB API (list endpoints: trending, now_playing, search).
 *
 * Field names match the API JSON; [SerializedName] used where the name differs from Kotlin convention.
 * Used for both list items and (partially) for detail; detail endpoint adds e.g. [runtime], [genres] array.
 */
data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("vote_count") val voteCount: Int = 0,
    // Detail endpoint only:
    @SerializedName("runtime") val runtime: Int? = null,
    @SerializedName("genres") val genres: List<GenreDto>? = null,
)

/**
 * Genre as returned by TMDB movie detail endpoint (e.g. {"id": 18, "name": "Drama"}).
 */
data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)
