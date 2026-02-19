package com.example.inshorts.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Bookmarked (saved) movie: one row per movie id.
 *
 * Toggling bookmark = insert or delete this row. No other fields needed.
 */
@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val movieId: Int,
)
