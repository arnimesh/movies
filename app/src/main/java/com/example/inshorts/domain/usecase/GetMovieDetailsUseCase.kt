package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case: observe a single movie's details by id.
 *
 * Delegates to [MovieRepository.getMovieById]. Caller should also trigger
 * [SyncMovieDetailsUseCase] on first load so that full details (genres, runtime, etc.) are fetched and stored.
 */
class GetMovieDetailsUseCase(
    private val repository: MovieRepository,
) {
    operator fun invoke(movieId: Int): Flow<Movie?> = repository.getMovieById(movieId)
}
