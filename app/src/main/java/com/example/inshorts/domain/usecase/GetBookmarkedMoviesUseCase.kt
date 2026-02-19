package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case: observe the list of bookmarked (saved) movies.
 *
 * Delegates to [MovieRepository.getBookmarkedMovies]. Bookmarks are updated
 * when the user toggles via [ToggleBookmarkUseCase].
 */
class GetBookmarkedMoviesUseCase(
    private val repository: MovieRepository,
) {
    operator fun invoke(): Flow<List<Movie>> = repository.getBookmarkedMovies()
}
