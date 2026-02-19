package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.repository.MovieRepository

/**
 * Use case: run a search against the TMDB API and store results in the local DB.
 *
 * Call when the user submits a search (or after debounce in the search screen). The repository
 * fetches from /search/movie and writes to Room; [SearchMoviesUseCase] with the same query
 * will then emit the results. Used for both "search button" and debounced-as-you-type flows.
 */
class SearchAndStoreMoviesUseCase(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(query: String) = repository.searchAndStore(query)
}
