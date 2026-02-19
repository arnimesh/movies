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

data class HomeUiState(
    val trending: List<Movie> = emptyList(),
    val nowPlaying: List<Movie> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
)

class HomeViewModel(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val syncTrendingMoviesUseCase: SyncTrendingMoviesUseCase,
    private val syncNowPlayingMoviesUseCase: SyncNowPlayingMoviesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
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
