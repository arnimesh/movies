package com.example.inshorts.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("vote_count") val voteCount: Int = 0,
    @SerializedName("runtime") val runtime: Int? = null,
    @SerializedName("genres") val genres: List<GenreDto>? = null,
)

data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)
