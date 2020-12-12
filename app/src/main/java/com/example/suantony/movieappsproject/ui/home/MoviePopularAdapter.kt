package com.example.suantony.movieappsproject.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suantony.movieappsproject.R
import com.example.suantony.movieappsproject.data.source.remote.response.MoviePopularItem
import com.example.suantony.movieappsproject.ui.detail.DetailMovieActivity
import kotlinx.android.synthetic.main.item_movie_popular.view.*

class MoviePopularAdapter internal constructor() :
    PagedListAdapter<MoviePopularItem, MoviePopularAdapter.MovieViewHolder>(
        DIFF_CALLBACK
    ) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MoviePopularItem>() {
            override fun areItemsTheSame(
                oldItem: MoviePopularItem,
                newItem: MoviePopularItem
            ): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(
                oldItem: MoviePopularItem,
                newItem: MoviePopularItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_popular, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = getItem(position)
        if (movies != null) {
            holder.bind(movies)
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: MoviePopularItem) {
            with(itemView) {
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
                    .into(iv_popular_movie)

                setOnClickListener {
                    val intent = Intent(context, DetailMovieActivity::class.java).apply {
                        putExtra(DetailMovieActivity.MOVIE_ID, movie.movieId.toString())
                        putExtra(DetailMovieActivity.MOVIE_DATE, movie.releaseDate)
                        putExtra(DetailMovieActivity.MOVIE_DESCRIPTION, movie.overview)
                        putExtra(DetailMovieActivity.MOVIE_BACKDROPPATH, movie.backdropPath)
                        putExtra(DetailMovieActivity.MOVIE_TITLE, movie.title)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }


}