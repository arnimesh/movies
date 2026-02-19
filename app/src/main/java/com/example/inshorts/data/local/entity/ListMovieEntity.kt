package com.example.inshorts.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Join entity: associates a movie with a list type and position for ordering.
 *
 * [listType] is either [LIST_TYPE_TRENDING] or [LIST_TYPE_NOW_PLAYING].
 * When we sync trending (or now_playing), we delete all rows for that type and insert the new list.
 */
@Entity(
    tableName = "list_movies",
    primaryKeys = ["listType", "movieId"],
    indices = [Index("listType")]
)
data class ListMovieEntity(
    val listType: String,
    val movieId: Int,
    val position: Int,
) {
    companion object {
        const val LIST_TYPE_TRENDING = "trending"
        const val LIST_TYPE_NOW_PLAYING = "now_playing"
    }
}
