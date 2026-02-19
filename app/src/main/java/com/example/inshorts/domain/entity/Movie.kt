package com.example.inshorts.domain.entity

/**
 * Domain entity representing a movie.
 *
 * Used across the app (UI, use cases). This layer has no Android or framework
 * dependencies—pure Kotlin so domain logic stays testable and independent.
 *
 * @param id TMDB movie id (unique).
 * @param title Display title.
 * @param posterPath Path segment for poster image (e.g. "/abc.jpg"); full URL is built using TMDB config base URL.
 * @param backdropPath Path segment for backdrop image.
 * @param overview Plot/summary text.
 * @param releaseDate Release date string (e.g. "2024-01-15").
 * @param voteAverage Average rating (0–10).
 * @param voteCount Number of votes.
 * @param genres Genre names (e.g. ["Drama", "Thriller"]); may be empty for list items until detail is loaded.
 * @param runtime Duration in minutes; null if not loaded (e.g. from list endpoint).
 */
data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val genres: List<String>,
    val runtime: Int?,
)
