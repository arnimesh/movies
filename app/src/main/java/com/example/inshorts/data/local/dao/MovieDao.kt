package com.example.inshorts.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inshorts.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for the [movies] table: insert/update and query by id.
 *
 * Used by [ListMovieDao] / [SearchResultDao] joins to get full movie data for lists.
 */
@Dao
interface MovieDao {

    /** Insert or replace a movie (e.g. after fetching from API). */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    /** Observe a single movie by id (for detail screen). */
    @Query("SELECT * FROM movies WHERE id = :id")
    fun getById(id: Int): Flow<MovieEntity?>

    /** Get movies by ids in order (for list screens: trending, now_playing, search, bookmarks). */
    @Query("SELECT * FROM movies WHERE id IN (:ids)")
    suspend fun getByIds(ids: List<Int>): List<MovieEntity>

    /** Get a single movie by id (suspend, for one-off reads in repository). */
    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getByIdOnce(id: Int): MovieEntity?
}
