package com.example.suantony.movieappsproject.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suantony.movieappsproject.R
import com.example.suantony.movieappsproject.data.source.remote.response.MovieTopRatedItem
import com.example.suantony.movieappsproject.ui.detail.DetailMovieActivity
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class MovieTopRatedAdapter internal constructor() :
    PagedListAdapter<MovieTopRatedItem, MovieTopRatedAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("d MMM yyyy")
    var date = Date()

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieTopRatedItem>() {
            override fun areItemsTheSame(
                oldItem: MovieTopRatedItem,
                newItem: MovieTopRatedItem
            ): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(
                oldItem: MovieTopRatedItem,
                newItem: MovieTopRatedItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = getItem(position)
        if (movies != null) {
            holder.bind(movies)
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: MovieTopRatedItem) {
            with(itemView) {
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
                    .into(iv_movie)

                tv_title_movie.text = movie.title
                date = inputFormat.parse(movie.releaseDate)
                tv_release_date.text = outputFormat.format(date)

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