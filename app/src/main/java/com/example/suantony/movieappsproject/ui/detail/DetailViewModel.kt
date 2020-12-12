package com.example.suantony.movieappsproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.suantony.movieappsproject.data.MovieRepository
import com.example.suantony.movieappsproject.data.source.local.entity.MovieEntity

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    lateinit var movieId: String
    var page: Int = 1

    fun getReviewMovie() = movieRepository.getReviewMovie(movieId, page)

    fun getFavoriteMovieById(): LiveData<MovieEntity> =
        LiveDataReactiveStreams.fromPublisher(movieRepository.getFavoriteMovieById(movieId))

    fun insertFavoriteMovie(movie: MovieEntity) = movieRepository.insertFavoriteMovie(movie)

    fun deleteFavoriteMovie(movie: MovieEntity) = movieRepository.deleteFavoriteMovie(movie)
}