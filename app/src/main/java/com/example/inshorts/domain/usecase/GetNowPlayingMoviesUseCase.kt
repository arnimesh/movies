package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetNowPlayingMoviesUseCase(
    private val repository: MovieRepository,
) {
    operator fun invoke(): Flow<List<Movie>> = repository.getNowPlayingMovies()
}
