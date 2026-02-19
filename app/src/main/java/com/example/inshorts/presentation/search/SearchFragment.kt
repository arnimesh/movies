package com.example.inshorts.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inshorts.MoviesApp
import com.example.inshorts.R
import com.example.inshorts.databinding.FragmentSearchBinding
import com.example.inshorts.presentation.common.SearchMovieListAdapter
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels {
        val app = requireActivity().application as MoviesApp
        SearchViewModel.Factory(
            app.appContainer.searchMoviesUseCase(),
            app.appContainer.searchAndStoreMoviesUseCase(),
        )
    }

    private val adapter = SearchMovieListAdapter { movie ->
        findNavController().navigate(R.id.detailFragment, Bundle().apply { putInt("movieId", movie.id) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchResults.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResults.adapter = adapter

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onQueryChanged(s?.toString()?.trim() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    adapter.submitList(state.results)
                    binding.searchLoader.visibility = if (state.loading) View.VISIBLE else View.GONE
                    binding.searchLoadingLabel.visibility = if (state.loading) View.VISIBLE else View.GONE
                    when {
                        state.loading -> {
                            binding.searchInitialHint.visibility = View.GONE
                            binding.searchEmptyState.visibility = View.GONE
                            binding.searchResults.visibility = View.VISIBLE
                        }
                        state.query.isBlank() -> {
                            binding.searchInitialHint.visibility = View.VISIBLE
                            binding.searchEmptyState.visibility = View.GONE
                            binding.searchResults.visibility = View.GONE
                        }
                        state.results.isEmpty() -> {
                            binding.searchInitialHint.visibility = View.GONE
                            binding.searchEmptyState.visibility = View.VISIBLE
                            binding.searchEmptyMessage.text = getString(R.string.search_no_results, state.query)
                            binding.searchResults.visibility = View.GONE
                        }
                        else -> {
                            binding.searchInitialHint.visibility = View.GONE
                            binding.searchEmptyState.visibility = View.GONE
                            binding.searchResults.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
