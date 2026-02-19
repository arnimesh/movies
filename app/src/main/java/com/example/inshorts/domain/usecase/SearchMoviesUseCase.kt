package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case: observe search results for a query.
 *
 * Delegates to [MovieRepository.searchMovies]. Results are populated when
 * [SearchAndStoreMoviesUseCase] is invoked with the same query.
 */
class SearchMoviesUseCase(
    private val repository: MovieRepository,
) {
    operator fun invoke(query: String): Flow<List<Movie>> = repository.searchMovies(query)
}
