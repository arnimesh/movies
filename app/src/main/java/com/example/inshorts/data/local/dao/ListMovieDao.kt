package com.example.inshorts.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inshorts.data.local.entity.ListMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ListMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<ListMovieEntity>)

    @Query("DELETE FROM list_movies WHERE listType = :listType")
    suspend fun deleteByListType(listType: String)

    @Query("SELECT movieId FROM list_movies WHERE listType = :listType ORDER BY position ASC")
    fun getMovieIdsByListType(listType: String): Flow<List<Int>>
}
