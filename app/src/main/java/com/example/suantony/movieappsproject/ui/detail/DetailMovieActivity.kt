package com.example.suantony.movieappsproject.ui.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.suantony.movieappsproject.R
import com.example.suantony.movieappsproject.data.Resource
import com.example.suantony.movieappsproject.data.source.local.entity.MovieEntity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.detail_bar.*
import kotlinx.android.synthetic.main.detail_content.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {
    private val detailViewModel: DetailViewModel by viewModel()
    private val reviewAdapter = ReviewAdapter()
    private var noDataToLoad: Boolean = false
    private var statusFavorite: Boolean = false

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        supportActionBar?.title = "Detail"
        val extra = intent.extras
        if (extra != null) {
            val movieId = extra.getString(MOVIE_ID)
            val movieBackdropPath = extra.getString(MOVIE_BACKDROPPATH)
            val movieDate = extra.getString(MOVIE_DATE)
            val movieDescription = extra.getString(MOVIE_DESCRIPTION)
            val movieTitle = extra.getString(MOVIE_TITLE)

            if (movieId != null) {
                detailViewModel.movieId = movieId
                loadReviewMovie()
            }

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${movieBackdropPath}")
                .into(iv_movie_detail)

            tv_title_movie_detail.text = movieTitle
            tv_release_date_detail.text = movieDate
            tv_description_detail.text = movieDescription

            with(rv_review) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = reviewAdapter
                isNestedScrollingEnabled
            }

            nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
                if (!noDataToLoad) {
                    if (v.getChildAt(v.childCount - 1) != null) {
                        if (scrollY >= v.getChildAt(v.childCount - 1)
                                .measuredHeight - v.measuredHeight &&
                            scrollY > oldScrollY
                        ) {
                            Log.d("scroll", "test")
                            loadReviewMovie()
                        }
                    }
                }
            })

            getStatusFavorite()
            image_button_favorite.setOnClickListener {
                statusFavorite = !statusFavorite

                if (statusFavorite) {
                    Toast.makeText(
                        this,
                        "Added to Favorite",
                        Toast.LENGTH_SHORT
                    ).show()
                    detailViewModel.insertFavoriteMovie(
                        MovieEntity(
                            movieId = movieId?.toInt(),
                            title = movieTitle,
                            releaseDate = movieDate,
                            overview = movieDescription,
                            backdropPath = movieBackdropPath,
                            isFavorite = true
                        )
                    )
                } else {
                    Toast.makeText(
                        this,
                        "Deleted from Favorite",
                        Toast.LENGTH_SHORT
                    ).show()

                    detailViewModel.deleteFavoriteMovie(
                        MovieEntity(
                            movieId = movieId?.toInt(),
                            title = movieTitle,
                            releaseDate = movieDate,
                            overview = movieDescription,
                            backdropPath = movieBackdropPath,
                            isFavorite = true
                        )
                    )
                }
                setStatusFavorite(statusFavorite)
            }


            bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
            image_button_share.setOnClickListener {
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                else
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }


    private fun loadReviewMovie() {
        Log.d("scroll", detailViewModel.page.toString())

        detailViewModel.getReviewMovie().observe(this, { review ->
            if (review != null) {
                when (review) {
                    is Resource.Loading -> {
                        Log.d("testing", "loading")
                    }
                    is Resource.Success -> {
                        Log.d("testing", review.data.toString())
                        reviewAdapter.setData(review.data)
                        detailViewModel.page += 1
                        noDataToLoad = false
                    }
                    is Resource.Empty -> {
                        noDataToLoad = true
                        Log.d("testing", "empty")
                    }
                    is Resource.Error -> {
                        Log.d("testing", "error")
                        if (review.message == "endofthelist") {
                            noDataToLoad = true
                        }
                    }
                }
            }
        })
    }

    private fun getStatusFavorite() {
        detailViewModel.getFavoriteMovieById().observe(this, { movie ->
            statusFavorite = movie != null
            setStatusFavorite(statusFavorite)
        })
    }


    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            image_button_favorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_red
                )
            )
        } else {
            image_button_favorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_border_red
                )
            )
        }
    }

    companion object {
        const val MOVIE_ID = "movie_id"
        const val MOVIE_DATE = "movie_date"
        const val MOVIE_DESCRIPTION = "movie_description"
        const val MOVIE_BACKDROPPATH = "movie_backdroppath"
        const val MOVIE_TITLE = "movie_title"
    }
}