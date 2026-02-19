package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.repository.MovieRepository

/**
 * Use case: fetch full movie details from the TMDB API and store in the local DB.
 *
 * Call when opening the movie detail screen (e.g. when [GetMovieDetailsUseCase] returns null
 * or to refresh). The repository will fetch /movie/{id} and update Room; [GetMovieDetailsUseCase]
 * flow will then emit the updated [Movie] (with genres, runtime, etc.).
 */
class SyncMovieDetailsUseCase(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int) = repository.syncMovieDetails(movieId)
}
