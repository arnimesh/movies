package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case: observe the list of trending movies.
 *
 * Delegates to [MovieRepository.getTrendingMovies]. The repository implementation
 * reads from the local DB (Room); data is populated when [SyncTrendingMoviesUseCase] (or equivalent) runs.
 */
class GetTrendingMoviesUseCase(
    private val repository: MovieRepository,
) {
    operator fun invoke(): Flow<List<Movie>> = repository.getTrendingMovies()
}
