package com.example.inshorts.presentation.saved

import android.os.Bundle
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
import com.example.inshorts.databinding.FragmentSavedBinding
import com.example.inshorts.presentation.common.MovieListAdapter
import kotlinx.coroutines.launch

class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SavedViewModel by viewModels {
        (requireActivity().application as MoviesApp).appContainer.let { c ->
            SavedViewModel.Factory(c.getBookmarkedMoviesUseCase())
        }
    }

    private val adapter = MovieListAdapter { movie ->
        findNavController().navigate(R.id.detailFragment, Bundle().apply { putInt("movieId", movie.id) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.savedList.layoutManager = LinearLayoutManager(requireContext())
        binding.savedList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { list ->
                    adapter.submitList(list)
                    binding.emptyMessage.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
