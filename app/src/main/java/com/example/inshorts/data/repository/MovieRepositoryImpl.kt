package com.example.inshorts.data.repository

import android.util.Log
import com.example.inshorts.data.local.dao.BookmarkDao
import com.example.inshorts.data.local.dao.ListMovieDao
import com.example.inshorts.data.local.dao.MovieDao
import com.example.inshorts.data.local.dao.SearchResultDao
import com.example.inshorts.data.local.entity.BookmarkEntity
import com.example.inshorts.data.local.entity.ListMovieEntity
import com.example.inshorts.data.local.entity.SearchResultEntity
import com.example.inshorts.data.mapper.toDomain
import com.example.inshorts.data.mapper.toEntity
import com.example.inshorts.data.remote.TmdbApiService
import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val api: TmdbApiService,
    private val movieDao: MovieDao,
    private val listMovieDao: ListMovieDao,
    private val searchResultDao: SearchResultDao,
    private val bookmarkDao: BookmarkDao,
) : MovieRepository {

    override fun getTrendingMovies(): Flow<List<Movie>> =
        listMovieDao.getMovieIdsByListType(ListMovieEntity.LIST_TYPE_TRENDING)
            .flatMapLatest { ids -> loadMoviesInOrder(ids) }

    override fun getNowPlayingMovies(): Flow<List<Movie>> =
        listMovieDao.getMovieIdsByListType(ListMovieEntity.LIST_TYPE_NOW_PLAYING)
            .flatMapLatest { ids -> loadMoviesInOrder(ids) }

    override fun getMovieById(id: Int): Flow<Movie?> =
        movieDao.getById(id).map { it?.toDomain() }

    override fun searchMovies(query: String): Flow<List<Movie>> =
        searchResultDao.getMovieIdsForQuery(query).flatMapLatest { ids -> loadMoviesInOrder(ids) }

    override fun getBookmarkedMovies(): Flow<List<Movie>> =
        bookmarkDao.getBookmarkedMovieIds().flatMapLatest { ids -> loadMoviesInOrder(ids) }

    private fun loadMoviesInOrder(ids: List<Int>): Flow<List<Movie>> = flow {
        if (ids.isEmpty()) {
            emit(emptyList())
            return@flow
        }
        val entities = withContext(Dispatchers.IO) { movieDao.getByIds(ids) }
        val ordered = ids.mapNotNull { id -> entities.find { it.id == id } }
        emit(ordered.map { it.toDomain() })
    }

    override suspend fun syncTrending() = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Syncing trending")
            val response = api.getTrendingMovies()
            val movies = response.results.map { it.toEntity() }
            movieDao.insertAll(movies)
            listMovieDao.deleteByListType(ListMovieEntity.LIST_TYPE_TRENDING)
            listMovieDao.insertAll(
                movies.mapIndexed { index, m ->
                    ListMovieEntity(ListMovieEntity.LIST_TYPE_TRENDING, m.id, index)
                }
            )
            Log.d(TAG, "Trending synced, count=${movies.size}")
        } catch (e: Exception) {
            Log.w(TAG, "syncTrending failed: ${e.message}", e)
        }
        Unit
    }

    override suspend fun syncNowPlaying() = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Syncing now playing")
            val response = api.getNowPlayingMovies()
            val movies = response.results.map { it.toEntity() }
            movieDao.insertAll(movies)
            listMovieDao.deleteByListType(ListMovieEntity.LIST_TYPE_NOW_PLAYING)
            listMovieDao.insertAll(
                movies.mapIndexed { index, m ->
                    ListMovieEntity(ListMovieEntity.LIST_TYPE_NOW_PLAYING, m.id, index)
                }
            )
            Log.d(TAG, "Now playing synced, count=${movies.size}")
        } catch (e: Exception) {
            Log.w(TAG, "syncNowPlaying failed: ${e.message}", e)
        }
        Unit
    }

    override suspend fun syncMovieDetails(id: Int) = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Syncing movie details id=$id")
            val dto = api.getMovieDetails(id)
            movieDao.insert(dto.toEntity())
            Log.d(TAG, "Movie details synced id=$id")
        } catch (e: Exception) {
            Log.w(TAG, "syncMovieDetails failed id=$id: ${e.message}", e)
        }
        Unit
    }

    override suspend fun searchAndStore(query: String) = withContext(Dispatchers.IO) {
        if (query.isBlank()) return@withContext
        try {
            Log.d(TAG, "Searching and storing query=$query")
            val response = api.searchMovies(query)
            val movies = response.results.map { it.toEntity() }
            movieDao.insertAll(movies)
            searchResultDao.deleteByQuery(query)
            searchResultDao.insertAll(
                movies.mapIndexed { index, m ->
                    SearchResultEntity(query, m.id, index)
                }
            )
            Log.d(TAG, "Search stored, count=${movies.size}")
        } catch (e: Exception) {
            Log.w(TAG, "searchAndStore failed query=$query: ${e.message}", e)
        }
        Unit
    }

    override suspend fun toggleBookmark(movieId: Int) = withContext(Dispatchers.IO) {
        val count = bookmarkDao.isBookmarked(movieId)
        val removed = count > 0
        if (removed) bookmarkDao.deleteByMovieId(movieId)
        else bookmarkDao.insert(BookmarkEntity(movieId))
        Log.d(TAG, "Toggle bookmark movieId=$movieId ${if (removed) "removed" else "added"}")
        Unit
    }

    companion object {
        private const val TAG = "Inshorts/Repo"
    }
}
