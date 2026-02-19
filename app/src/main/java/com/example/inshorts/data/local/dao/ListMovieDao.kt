package com.example.inshorts.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inshorts.data.local.entity.ListMovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [list_movies]: which movies belong to trending or now_playing and in what order.
 *
 * [getMovieIdsByListType] returns ordered ids; repository then loads [MovieEntity] from [MovieDao] and maps to domain.
 */
@Dao
interface ListMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<ListMovieEntity>)

    /** Remove all entries for a list type before inserting fresh data from API. */
    @Query("DELETE FROM list_movies WHERE listType = :listType")
    suspend fun deleteByListType(listType: String)

    /** Ordered movie ids for a list type (e.g. "trending" or "now_playing"). */
    @Query("SELECT movieId FROM list_movies WHERE listType = :listType ORDER BY position ASC")
    fun getMovieIdsByListType(listType: String): Flow<List<Int>>
}
