package com.example.inshorts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inshorts.data.local.dao.BookmarkDao
import com.example.inshorts.data.local.dao.ListMovieDao
import com.example.inshorts.data.local.dao.MovieDao
import com.example.inshorts.data.local.dao.SearchResultDao
import com.example.inshorts.data.local.entity.BookmarkEntity
import com.example.inshorts.data.local.entity.ListMovieEntity
import com.example.inshorts.data.local.entity.MovieEntity
import com.example.inshorts.data.local.entity.SearchResultEntity

@Database(
    entities = [
        MovieEntity::class,
        ListMovieEntity::class,
        SearchResultEntity::class,
        BookmarkEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun listMovieDao(): ListMovieDao
    abstract fun searchResultDao(): SearchResultDao
    abstract fun bookmarkDao(): BookmarkDao
}
