package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.repository.MovieRepository

/**
 * Use case: toggle bookmark state for a movie.
 *
 * Delegates to [MovieRepository.toggleBookmark]. The repository implementation
 * updates the local DB only; UI observing [GetBookmarkedMoviesUseCase] or
 * [GetMovieDetailsUseCase] will see the update via Flow.
 */
class ToggleBookmarkUseCase(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int) = repository.toggleBookmark(movieId)
}
