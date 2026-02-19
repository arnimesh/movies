package com.example.inshorts.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Response body for GET /configuration (TMDB image base URLs and sizes).
 *
 * We use [images.secureBaseUrl] and a fixed poster size (e.g. "w500") to build
 * full image URLs: secureBaseUrl + "w500" + poster_path from a movie.
 */
data class ConfigurationDto(
    @SerializedName("images") val images: ImagesDto? = null,
)

data class ImagesDto(
    @SerializedName("secure_base_url") val secureBaseUrl: String = "",
    @SerializedName("poster_sizes") val posterSizes: List<String>? = null,
    @SerializedName("backdrop_sizes") val backdropSizes: List<String>? = null,
)
