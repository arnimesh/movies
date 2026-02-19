package com.example.inshorts.domain.repository

import com.example.inshorts.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTrendingMovies(): Flow<List<Movie>>

    fun getNowPlayingMovies(): Flow<List<Movie>>

    fun getMovieById(id: Int): Flow<Movie?>

    fun searchMovies(query: String): Flow<List<Movie>>

    fun getBookmarkedMovies(): Flow<List<Movie>>

    suspend fun syncTrending()

    suspend fun syncNowPlaying()

    suspend fun syncMovieDetails(id: Int)

    suspend fun searchAndStore(query: String)

    suspend fun toggleBookmark(movieId: Int)
}
