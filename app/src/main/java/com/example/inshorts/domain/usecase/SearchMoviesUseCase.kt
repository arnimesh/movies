package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase(
    private val repository: MovieRepository,
) {
    operator fun invoke(query: String): Flow<List<Movie>> = repository.searchMovies(query)
}
