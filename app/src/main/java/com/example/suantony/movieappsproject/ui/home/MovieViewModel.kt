package com.example.suantony.movieappsproject.ui.home

import androidx.lifecycle.ViewModel
import com.example.suantony.movieappsproject.data.MovieRepository

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getPopularMovie() = movieRepository.getPopularMovie()

    fun getTopRatedMovie() = movieRepository.getTopRatedMovie()

    fun getNowPlayingMovie() = movieRepository.getNowPlayingMovie()

}