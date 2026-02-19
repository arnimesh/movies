package com.example.inshorts.data.mapper

import com.example.inshorts.data.local.entity.MovieEntity
import com.example.inshorts.data.remote.dto.MovieDto
import com.example.inshorts.domain.entity.Movie

fun MovieDto.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
    backdropPath = backdropPath,
    overview = overview ?: "",
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    genres = genres?.joinToString(",") { it.name } ?: "",
    runtime = runtime,
)

fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    posterPath = posterPath,
    backdropPath = backdropPath,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    genres = if (genres.isBlank()) emptyList() else genres.split(",").map { it.trim() },
    runtime = runtime,
)
