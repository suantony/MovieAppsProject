package com.example.suantony.movieappsproject.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.suantony.movieappsproject.data.source.local.entity.MovieEntity
import com.example.suantony.movieappsproject.data.source.remote.response.MovieNowPlayingItem
import com.example.suantony.movieappsproject.data.source.remote.response.MoviePopularItem
import com.example.suantony.movieappsproject.data.source.remote.response.MovieTopRatedItem
import com.example.suantony.movieappsproject.data.source.remote.response.ReviewItem
import io.reactivex.Flowable

interface IMovieDataResource {
    fun getPopularMovie(): LiveData<Resource<PagedList<MoviePopularItem>>>
    fun getTopRatedMovie(): LiveData<Resource<PagedList<MovieTopRatedItem>>>
    fun getNowPlayingMovie(): LiveData<Resource<PagedList<MovieNowPlayingItem>>>
    fun getReviewMovie(movieId: String, page: Int): LiveData<Resource<List<ReviewItem>>>

    fun getAllFavoriteMovie(): Flowable<List<MovieEntity>>
    fun getFavoriteMovieById(movieId: String): Flowable<MovieEntity>
    fun insertFavoriteMovie(movie: MovieEntity)
    fun deleteFavoriteMovie(movie: MovieEntity)
}