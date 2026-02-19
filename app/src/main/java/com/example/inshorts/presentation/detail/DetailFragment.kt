package com.example.inshorts.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.inshorts.MoviesApp
import coil.load
import com.example.inshorts.databinding.FragmentDetailBinding
import com.example.inshorts.presentation.common.TMDB_IMAGE_BASE
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels {
        val movieId = requireArguments().getInt("movieId", 0)
        val app = requireActivity().application as MoviesApp
        DetailViewModel.Factory(
            movieId,
            app.appContainer.getMovieDetailsUseCase(),
            app.appContainer.getBookmarkedMoviesUseCase(),
            app.appContainer.syncMovieDetailsUseCase(),
            app.appContainer.toggleBookmarkUseCase(),
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBookmark.setOnClickListener { viewModel.toggleBookmark() }
        binding.btnShare.setOnClickListener {
            viewModel.state.value.movie?.let { movie ->
                val deeplink = "inshorts://movie/${movie.id}"
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, deeplink)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(sendIntent, getString(com.example.inshorts.R.string.share_movie)))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.detailLoader.visibility = if (state.loading) View.VISIBLE else View.GONE
                    state.movie?.let { movie ->
                        binding.title.text = movie.title
                        binding.rating.text = "★ %.1f".format(movie.voteAverage)
                        binding.releaseDate.text = movie.releaseDate
                        binding.overview.text = movie.overview
                        binding.genres.text = movie.genres.joinToString(", ")
                        binding.poster.load(TMDB_IMAGE_BASE + (movie.posterPath ?: "")) { crossfade(true) }
                        binding.backdrop.load("https://image.tmdb.org/t/p/w780" + (movie.backdropPath ?: "")) { crossfade(true) }
                        binding.btnBookmark.text = if (state.isBookmarked) getString(com.example.inshorts.R.string.bookmarked) else getString(com.example.inshorts.R.string.bookmark)
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
