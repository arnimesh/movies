package com.example.inshorts.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.domain.usecase.SearchAndStoreMoviesUseCase
import com.example.inshorts.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * UI state for search: current query, results list, loading.
 */
data class SearchUiState(
    val query: String = "",
    val results: List<Movie> = emptyList(),
    val loading: Boolean = false,
)

/**
 * ViewModel for search: [onQueryChanged] is called as the user types; we debounce 400ms then
 * call [SearchAndStoreMoviesUseCase]; we observe [SearchMoviesUseCase](query) so results update when DB has data.
 */
class SearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val searchAndStoreMoviesUseCase: SearchAndStoreMoviesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    private var debounceJob: Job? = null
    private var resultsJob: Job? = null

    /** Call from fragment when search input text changes. Debounces then fetches; observes results flow for this query. */
    fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }
        resultsJob?.cancel()
        if (query.isBlank()) {
            _state.update { it.copy(results = emptyList()) }
            return
        }
        resultsJob = viewModelScope.launch {
            searchMoviesUseCase(query).collect { list ->
                _state.update { it.copy(results = list) }
            }
        }
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(400)
            Log.d(TAG, "Search triggered query=$query")
            _state.update { it.copy(loading = true) }
            searchAndStoreMoviesUseCase(query)
            _state.update { it.copy(loading = false) }
        }
    }

    companion object {
        private const val TAG = "Inshorts/SearchVM"
    }

    class Factory(
        private val searchMoviesUseCase: SearchMoviesUseCase,
        private val searchAndStoreMoviesUseCase: SearchAndStoreMoviesUseCase,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SearchViewModel(searchMoviesUseCase, searchAndStoreMoviesUseCase) as T
    }
}
