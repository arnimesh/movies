package com.example.inshorts.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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
