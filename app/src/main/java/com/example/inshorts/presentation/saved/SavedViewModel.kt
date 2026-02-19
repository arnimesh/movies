package com.example.inshorts.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.domain.usecase.GetBookmarkedMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the saved (bookmarked) movies tab: observes [GetBookmarkedMoviesUseCase] and exposes the list.
 */
class SavedViewModel(
    private val getBookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<List<Movie>>(emptyList())
    val state: StateFlow<List<Movie>> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getBookmarkedMoviesUseCase().collect { list ->
                _state.update { list }
            }
        }
    }

    class Factory(
        private val getBookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SavedViewModel(getBookmarkedMoviesUseCase) as T
    }
}
