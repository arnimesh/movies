package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.repository.MovieRepository

class ToggleBookmarkUseCase(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int) = repository.toggleBookmark(movieId)
}
