package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case: observe the list of now-playing movies.
 *
 * Delegates to [MovieRepository.getNowPlayingMovies]. Data is updated when
 * [SyncNowPlayingMoviesUseCase] (or equivalent) runs.
 */
class GetNowPlayingMoviesUseCase(
    private val repository: MovieRepository,
) {
    operator fun invoke(): Flow<List<Movie>> = repository.getNowPlayingMovies()
}
