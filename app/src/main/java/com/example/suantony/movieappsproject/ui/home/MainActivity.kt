package com.example.suantony.movieappsproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suantony.movieappsproject.R
import com.example.suantony.movieappsproject.data.Resource
import com.example.suantony.movieappsproject.ui.favorite.FavoriteMovieActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "MOVIE"

        val moviePopularAdapter = MoviePopularAdapter()
        val movieTopRatedAdapter = MovieTopRatedAdapter()
        val movieNowPlayingAdapter = MovieNowPlayingAdapter()

        movieViewModel.getPopularMovie().observe(this, { popular ->
            if (popular != null) {
                when (popular) {
                    is Resource.Loading -> {
                        Log.d("testing", "loading")
                        pb_popular_movie.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        Log.d("testing", popular.data.toString())
                        pb_popular_movie.visibility = View.GONE
                        moviePopularAdapter.submitList(popular.data)
                        moviePopularAdapter.notifyDataSetChanged()
                    }
                    is Resource.Error -> {
                        Log.d("testing", "error")
                        pb_popular_movie.visibility = View.GONE
                    }
                }
            }
        })

        movieViewModel.getTopRatedMovie().observe(this, { topRated ->
            if (topRated != null) {
                when (topRated) {
                    is Resource.Loading -> {
                        Log.d("testing", "loading")
                        pb_top_rated.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        Log.d("testing", topRated.data.toString())
                        movieTopRatedAdapter.submitList(topRated.data)
                        movieTopRatedAdapter.notifyDataSetChanged()
                        pb_top_rated.visibility = View.GONE

                    }
                    is Resource.Error -> {
                        Log.d("testing", "error")
                        pb_top_rated.visibility = View.GONE

                    }
                }
            }
        })

        movieViewModel.getNowPlayingMovie().observe(this, { nowPlaying ->
            if (nowPlaying != null) {
                when (nowPlaying) {
                    is Resource.Loading -> {
                        Log.d("testing", "loading")
                        pb_now_playing.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        Log.d("testing", nowPlaying.data.toString())
                        movieNowPlayingAdapter.submitList(nowPlaying.data)
                        movieNowPlayingAdapter.notifyDataSetChanged()
                        pb_now_playing.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        Log.d("testing", "error")
                        pb_now_playing.visibility = View.GONE
                    }
                }
            }
        })

        with(rv_popular_movie) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            this.adapter = moviePopularAdapter
        }

        with(rv_top_rated) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            this.adapter = movieTopRatedAdapter
        }

        with(rv_now_playing) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            this.adapter = movieNowPlayingAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favoriteMenu -> {
                startActivity(Intent(this, FavoriteMovieActivity::class.java))
                return true
            }
            else -> return true
        }
    }
}