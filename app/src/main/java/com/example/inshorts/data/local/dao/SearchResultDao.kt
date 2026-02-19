package com.example.inshorts.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inshorts.data.local.entity.SearchResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<SearchResultEntity>)

    @Query("DELETE FROM search_results WHERE query = :query")
    suspend fun deleteByQuery(query: String)

    @Query("SELECT movieId FROM search_results WHERE query = :query ORDER BY position ASC")
    fun getMovieIdsForQuery(query: String): Flow<List<Int>>
}
