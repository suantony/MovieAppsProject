package com.example.suantony.movieappsproject.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suantony.movieappsproject.R
import kotlinx.android.synthetic.main.activity_favorite_movie.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteMovieActivity : AppCompatActivity() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movie)
        supportActionBar?.title = "Favorite Movie"
        val favoriteAdapter = FavoriteAdapter()

        favoriteViewModel.getAllFavoriteMovie().observe(this, { favorite ->
            favoriteAdapter.setData(favorite)
            view_empty.visibility = if (favorite.isNotEmpty()) View.GONE else View.VISIBLE
        })

        with(rv_favorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }

    }
}