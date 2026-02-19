package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.repository.MovieRepository

class SearchAndStoreMoviesUseCase(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(query: String) = repository.searchAndStore(query)
}
