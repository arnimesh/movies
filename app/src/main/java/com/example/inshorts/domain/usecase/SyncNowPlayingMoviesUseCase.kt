package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.repository.MovieRepository

/**
 * Use case: sync now-playing movies from the TMDB API into the local DB.
 *
 * Call from the home screen alongside [SyncTrendingMoviesUseCase]. Updates
 * the stream exposed by [GetNowPlayingMoviesUseCase].
 */
class SyncNowPlayingMoviesUseCase(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke() = repository.syncNowPlaying()
}
