package com.example.inshorts.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inshorts.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getById(id: Int): Flow<MovieEntity?>

    @Query("SELECT * FROM movies WHERE id IN (:ids)")
    suspend fun getByIds(ids: List<Int>): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getByIdOnce(id: Int): MovieEntity?
}
