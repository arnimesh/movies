package com.example.inshorts.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inshorts.databinding.ItemMovieSearchBinding
import com.example.inshorts.domain.entity.Movie
import coil.load

/**
 * RecyclerView adapter for search results: full-width rows (poster left, title + rating right).
 * Uses [item_movie_search.xml]. [onMovieClick] is invoked when an item is clicked.
 */
class SearchMovieListAdapter(
    private val onMovieClick: (Movie) -> Unit,
) : ListAdapter<Movie, SearchMovieListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onMovieClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemMovieSearchBinding,
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
