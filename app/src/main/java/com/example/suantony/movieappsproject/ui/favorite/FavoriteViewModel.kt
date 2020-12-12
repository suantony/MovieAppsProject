package com.example.suantony.movieappsproject.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.suantony.movieappsproject.data.MovieRepository
import com.example.suantony.movieappsproject.data.source.local.entity.MovieEntity

class FavoriteViewModel(private val repository: MovieRepository) : ViewModel() {
    fun getAllFavoriteMovie(): LiveData<List<MovieEntity>> =
        LiveDataReactiveStreams.fromPublisher(repository.getAllFavoriteMovie())


}