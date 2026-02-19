package com.example.inshorts.domain.repository

import com.example.inshorts.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for movie data (Clean Architecture: defined in domain, implemented in data).
 *
 * The implementation will use Room as the source of truth and sync from the TMDB API when online.
 * All getters expose Flow so the UI can react to DB updates; sync methods trigger network and DB writes.
 */
interface MovieRepository {

    // ---------- Getters (observe from local DB; implementation maps Room entities to [Movie]) ----------

    /** Stream of trending movies (updated when [syncTrending] runs). */
    fun getTrendingMovies(): Flow<List<Movie>>

    /** Stream of now-playing movies (updated when [syncNowPlaying] runs). */
    fun getNowPlayingMovies(): Flow<List<Movie>>

    /** Stream of a single movie by id; null until loaded. Updated when [syncMovieDetails] runs. */
    fun getMovieById(id: Int): Flow<Movie?>

    /** Stream of search results for the given query (updated when [searchAndStore] runs). */
    fun searchMovies(query: String): Flow<List<Movie>>

    /** Stream of bookmarked movies. */
    fun getBookmarkedMovies(): Flow<List<Movie>>

    // ---------- Sync / write (network + DB; implementation handles offline by no-op or error) ----------

    /** Fetches trending movies from API and writes to local DB. */
    suspend fun syncTrending()

    /** Fetches now-playing movies from API and writes to local DB. */
    suspend fun syncNowPlaying()

    /** Fetches full movie details from API and writes/updates in local DB. */
    suspend fun syncMovieDetails(id: Int)

    /** Fetches search results for [query] from API and writes to local DB. */
    suspend fun searchAndStore(query: String)

    /** Toggles bookmark for the given movie (local DB only). */
    suspend fun toggleBookmark(movieId: Int)
}
