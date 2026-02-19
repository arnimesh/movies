package com.example.inshorts.di

import android.content.Context
import android.util.Log
import com.example.inshorts.BuildConfig
import com.example.inshorts.data.local.AppDatabase
import com.example.inshorts.data.remote.TmdbApiProvider
import com.example.inshorts.data.repository.MovieRepositoryImpl
import com.example.inshorts.domain.repository.MovieRepository
import com.example.inshorts.domain.usecase.GetBookmarkedMoviesUseCase
import com.example.inshorts.domain.usecase.GetMovieDetailsUseCase
import com.example.inshorts.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.inshorts.domain.usecase.GetTrendingMoviesUseCase
import com.example.inshorts.domain.usecase.SearchAndStoreMoviesUseCase
import com.example.inshorts.domain.usecase.SearchMoviesUseCase
import com.example.inshorts.domain.usecase.SyncMovieDetailsUseCase
import com.example.inshorts.domain.usecase.SyncNowPlayingMoviesUseCase
import com.example.inshorts.domain.usecase.SyncTrendingMoviesUseCase
import com.example.inshorts.domain.usecase.ToggleBookmarkUseCase

class AppContainer(context: Context) {

    private val database: AppDatabase by lazy {
        Log.d(TAG, "Creating database")
        androidx.room.Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "movies_db"
        ).build()
    }

    private val repository: MovieRepository by lazy {
        Log.d(TAG, "Creating repository")
        val api = TmdbApiProvider.create(BuildConfig.TMDB_API_KEY)
        MovieRepositoryImpl(
            api = api,
            movieDao = database.movieDao(),
            listMovieDao = database.listMovieDao(),
            searchResultDao = database.searchResultDao(),
            bookmarkDao = database.bookmarkDao(),
        )
    }

    companion object {
        private const val TAG = "Inshorts/DI"
    }

    fun getTrendingMoviesUseCase() = GetTrendingMoviesUseCase(repository)
    fun getNowPlayingMoviesUseCase() = GetNowPlayingMoviesUseCase(repository)
    fun getMovieDetailsUseCase() = GetMovieDetailsUseCase(repository)
    fun searchMoviesUseCase() = SearchMoviesUseCase(repository)
    fun getBookmarkedMoviesUseCase() = GetBookmarkedMoviesUseCase(repository)
    fun toggleBookmarkUseCase() = ToggleBookmarkUseCase(repository)
    fun syncTrendingMoviesUseCase() = SyncTrendingMoviesUseCase(repository)
    fun syncNowPlayingMoviesUseCase() = SyncNowPlayingMoviesUseCase(repository)
    fun syncMovieDetailsUseCase() = SyncMovieDetailsUseCase(repository)
    fun searchAndStoreMoviesUseCase() = SearchAndStoreMoviesUseCase(repository)
}
