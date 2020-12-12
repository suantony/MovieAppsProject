package com.example.suantony.movieappsproject.data.source.local

import com.example.suantony.movieappsproject.data.source.local.entity.MovieEntity
import com.example.suantony.movieappsproject.data.source.local.room.MovieDao
import io.reactivex.Flowable

class LocalDataSource(private val movieDao: MovieDao) {
    fun getAllFavoriteMovie(): Flowable<List<MovieEntity>> = movieDao.getAllFavoriteMovie()

    fun getFavoriteMovieById(movieId: String): Flowable<MovieEntity> =
        movieDao.getFavoriteMovieById(movieId)

    fun insertFavoriteMovie(movie: MovieEntity) = movieDao.insertFavoriteMovie(movie)

    fun deleteFavoriteMovie(movie: MovieEntity) = movieDao.deleteFavoriteMovie(movie)
}