package com.example.inshorts.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inshorts.domain.entity.Movie
import com.example.inshorts.databinding.ItemMovieBinding
import coil.load

/** TMDB image base URL for poster size w500 (used for list and detail). */
const val TMDB_IMAGE_BASE = "https://image.tmdb.org/t/p/w500"

/**
 * RecyclerView adapter for a list of [Movie] items (poster, title, rating).
 * [onMovieClick] is invoked when an item is clicked (e.g. to navigate to detail).
 */
class MovieListAdapter(
    private val onMovieClick: (Movie) -> Unit,
) : ListAdapter<Movie, MovieListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onMovieClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemMovieBinding,
        private val onMovieClick: (Movie) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.title.text = movie.title
            binding.rating.text = "★ %.1f".format(movie.voteAverage)
            binding.poster.load(TMDB_IMAGE_BASE + (movie.posterPath ?: "")) {
                crossfade(true)
            }
            binding.root.setOnClickListener { onMovieClick(movie) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(a: Movie, b: Movie) = a.id == b.id
        override fun areContentsTheSame(a: Movie, b: Movie) = a == b
    }
}
