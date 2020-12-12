package com.example.suantony.movieappsproject.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suantony.movieappsproject.R
import com.example.suantony.movieappsproject.data.source.local.entity.MovieEntity
import com.example.suantony.movieappsproject.ui.detail.DetailMovieActivity
import kotlinx.android.synthetic.main.item_favorite.view.*
import java.util.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MovieViewHolder>() {

    private var listData = ArrayList<MovieEntity>()

    fun setData(newListData: List<MovieEntity>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: MovieEntity) {
            with(itemView) {
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
                    .into(iv_movie_fav)

                tv_title_movie_fav.text = movie.title
                tv_release_date_fav.text = movie.releaseDate
                tv_description_fav.text = movie.overview

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