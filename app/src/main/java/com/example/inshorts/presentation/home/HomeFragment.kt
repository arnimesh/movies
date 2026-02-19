package com.example.inshorts.presentation.home

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
import com.example.inshorts.databinding.FragmentHomeBinding
import com.example.inshorts.presentation.common.MovieListAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        val app = requireActivity().application as MoviesApp
        HomeViewModel.Factory(
            app.appContainer.getTrendingMoviesUseCase(),
            app.appContainer.getNowPlayingMoviesUseCase(),
            app.appContainer.syncTrendingMoviesUseCase(),
            app.appContainer.syncNowPlayingMoviesUseCase(),
        )
    }

    private val navigateToDetail: (com.example.inshorts.domain.entity.Movie) -> Unit = { movie ->
        findNavController().navigate(R.id.detailFragment, Bundle().apply { putInt("movieId", movie.id) })
    }

    private val trendingAdapter = MovieListAdapter(navigateToDetail)
    private val nowPlayingAdapter = MovieListAdapter(navigateToDetail)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.trendingList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.trendingList.adapter = trendingAdapter
        binding.nowPlayingList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.nowPlayingList.adapter = nowPlayingAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.homeLoader.visibility = if (state.loading) View.VISIBLE else View.GONE
                    trendingAdapter.submitList(state.trending)
                    nowPlayingAdapter.submitList(state.nowPlaying)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
