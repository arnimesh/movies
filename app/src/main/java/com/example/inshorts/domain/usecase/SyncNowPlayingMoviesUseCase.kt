package com.example.inshorts.domain.usecase

import com.example.inshorts.domain.repository.MovieRepository

class SyncNowPlayingMoviesUseCase(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke() = repository.syncNowPlaying()
}
