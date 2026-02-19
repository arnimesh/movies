package com.example.inshorts.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Stores search results: for a given [query], the list of [movieId]s in [position] order.
 *
 * When the user searches for a new query, we replace all rows for that query with the new results.
 * We keep one row per (query, movieId) so we can support multiple search result sets (last query per session).
 */
@Entity(
    tableName = "search_results",
    primaryKeys = ["query", "movieId"],
    indices = [Index("query")]
)
data class SearchResultEntity(
    val query: String,
    val movieId: Int,
    val position: Int,
)
