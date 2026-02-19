package com.example.inshorts.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for a movie (single table holding all movie fields).
 *
 * Used as cache for list and detail data from TMDB. We do not duplicate movies per list;
 * list membership is stored in [ListMovieEntity] (trending / now_playing) and [SearchResultEntity] (search).
 * [BookmarkEntity] stores which movies are bookmarked.
 *
 * [genres] is stored as comma-separated names (e.g. "Drama,Thriller") to avoid a separate table.
 */
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    /** Comma-separated genre names; empty string if none. */
    val genres: String,
    /** Runtime in minutes; null if not yet loaded (e.g. from list endpoint only). */
    val runtime: Int?,
)
