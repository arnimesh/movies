package com.example.inshorts.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ConfigurationDto(
    @SerializedName("images") val images: ImagesDto? = null,
)

data class ImagesDto(
    @SerializedName("secure_base_url") val secureBaseUrl: String = "",
    @SerializedName("poster_sizes") val posterSizes: List<String>? = null,
    @SerializedName("backdrop_sizes") val backdropSizes: List<String>? = null,
)
