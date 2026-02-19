package com.example.inshorts.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inshorts.data.local.entity.SearchResultEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [search_results]: which movies are in the result set for a given query.
 *
 * When search runs, we delete previous results for that query and insert the new list.
 * [getMovieIdsForQuery] returns ordered ids; repository loads movies and maps to domain.
 */
@Dao
interface SearchResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<SearchResultEntity>)

    @Query("DELETE FROM search_results WHERE query = :query")
    suspend fun deleteByQuery(query: String)

    @Query("SELECT movieId FROM search_results WHERE query = :query ORDER BY position ASC")
    fun getMovieIdsForQuery(query: String): Flow<List<Int>>
}
