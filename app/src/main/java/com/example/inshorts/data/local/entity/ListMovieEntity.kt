package com.example.inshorts.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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
