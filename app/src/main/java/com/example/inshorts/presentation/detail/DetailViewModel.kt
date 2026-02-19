package com.example.inshorts.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.domain.usecase.GetBookmarkedMoviesUseCase
import com.example.inshorts.domain.usecase.GetMovieDetailsUseCase
import com.example.inshorts.domain.usecase.SyncMovieDetailsUseCase
import com.example.inshorts.domain.usecase.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailUiState(
    val movie: Movie? = null,
    val loading: Boolean = false,
    val isBookmarked: Boolean = false,
)

class DetailViewModel(
    private val movieId: Int,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getBookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase,
    private val syncMovieDetailsUseCase: SyncMovieDetailsUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state.asStateFlow()

    init {
        Log.d(TAG, "Loading detail for movieId=$movieId")
        viewModelScope.launch {
            getMovieDetailsUseCase(movieId).collect { movie ->
                _state.update { it.copy(movie = movie) }
            }
        }
        viewModelScope.launch {
            getBookmarkedMoviesUseCase().collect { list ->
                _state.update { it.copy(isBookmarked = list.any { it.id == movieId }) }
            }
        }
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            syncMovieDetailsUseCase(movieId)
            _state.update { it.copy(loading = false) }
        }
    }

    fun toggleBookmark() {
        Log.d(TAG, "Toggle bookmark movieId=$movieId")
        viewModelScope.launch { toggleBookmarkUseCase(movieId) }
    }

    companion object {
        private const val TAG = "Inshorts/DetailVM"
    }

    class Factory(
        private val movieId: Int,
        private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
        private val getBookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase,
        private val syncMovieDetailsUseCase: SyncMovieDetailsUseCase,
        private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            DetailViewModel(movieId, getMovieDetailsUseCase, getBookmarkedMoviesUseCase, syncMovieDetailsUseCase, toggleBookmarkUseCase) as T
    }
}
