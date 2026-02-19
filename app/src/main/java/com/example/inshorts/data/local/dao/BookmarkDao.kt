package com.example.inshorts.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inshorts.data.local.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [bookmarks]: set of movie ids that the user has bookmarked.
 *
 * Toggle = insert or delete one row. [getBookmarkedMovieIds] streams the current set;
 * repository joins with [MovieDao] to get full movie data.
 */
@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE movieId = :movieId")
    suspend fun deleteByMovieId(movieId: Int)

    @Query("SELECT movieId FROM bookmarks")
    fun getBookmarkedMovieIds(): Flow<List<Int>>

    @Query("SELECT COUNT(*) FROM bookmarks WHERE movieId = :movieId")
    suspend fun isBookmarked(movieId: Int): Int
}
