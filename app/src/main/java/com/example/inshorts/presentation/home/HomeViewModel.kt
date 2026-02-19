package com.example.inshorts.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.inshorts.domain.usecase.GetTrendingMoviesUseCase
import com.example.inshorts.domain.usecase.SyncNowPlayingMoviesUseCase
import com.example.inshorts.domain.usecase.SyncTrendingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * UI state for the home screen: trending and now-playing lists, loading, and error.
 */
data class HomeUiState(
    val trending: List<Movie> = emptyList(),
    val nowPlaying: List<Movie> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
)

/**
 * ViewModel for the home screen: observes trending and now-playing from use cases,
 * triggers sync on start so data is fetched from API and written to DB (then flows emit from DB).
 */
class HomeViewModel(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val syncTrendingMoviesUseCase: SyncTrendingMoviesUseCase,
    private val syncNowPlayingMoviesUseCase: SyncNowPlayingMoviesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        // Observe trending: use case returns Flow from repository (Room)
        viewModelScope.launch {
            getTrendingMoviesUseCase().collect { list ->
                _state.update { it.copy(trending = list) }
            }
        }
        viewModelScope.launch {
            getNowPlayingMoviesUseCase().collect { list ->
                _state.update { it.copy(nowPlaying = list) }
            }
        }
        // Trigger sync so API is fetched and DB is updated (offline: no-op, cached data stays)
        viewModelScope.launch {
            Log.d(TAG, "Sync started")
            _state.update { it.copy(loading = true) }
            syncTrendingMoviesUseCase()
            syncNowPlayingMoviesUseCase()
            _state.update { it.copy(loading = false) }
            Log.d(TAG, "Sync completed")
        }
    }

    companion object {
        private const val TAG = "Inshorts/HomeVM"
    }

    class Factory(
        private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
        private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
        private val syncTrendingMoviesUseCase: SyncTrendingMoviesUseCase,
        private val syncNowPlayingMoviesUseCase: SyncNowPlayingMoviesUseCase,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            HomeViewModel(
                getTrendingMoviesUseCase,
                getNowPlayingMoviesUseCase,
                syncTrendingMoviesUseCase,
                syncNowPlayingMoviesUseCase,
            ) as T
    }
}
