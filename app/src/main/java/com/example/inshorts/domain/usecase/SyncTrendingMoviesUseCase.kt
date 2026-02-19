package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.repository.MovieRepository

/**
 * Use case: sync trending movies from the TMDB API into the local DB.
 *
 * Call from the home screen (e.g. on first load or pull-to-refresh). The repository
 * implementation will fetch from the network and write to Room; [GetTrendingMoviesUseCase]
 * streams will then emit the updated list. On network failure, implementation may no-op or emit error.
 */
class SyncTrendingMoviesUseCase(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke() = repository.syncTrending()
}
