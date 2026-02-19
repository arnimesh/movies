package com.example.inshorts.data.mapper

import com.example.inshorts.data.local.entity.MovieEntity
import com.example.inshorts.data.remote.dto.MovieDto
import com.example.inshorts.domain.entity.Movie

/**
 * Mappers between data layer (DTO / Room entity) and domain [Movie].
 *
 * Used by [MovieRepositoryImpl] when writing from API and when reading from DB for use cases.
 */

/** Map API DTO to Room entity (for insert/update). Genres from detail endpoint become comma-separated string. */
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

/** Map Room entity to domain model (for use case streams). */
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
