package com.example.suantony.movieappsproject.data.source.remote.network

import com.example.suantony.movieappsproject.data.source.remote.response.MovieNowPlayingResponse
import com.example.suantony.movieappsproject.data.source.remote.response.MoviePopularResponse
import com.example.suantony.movieappsproject.data.source.remote.response.MovieTopRatedResponse
import com.example.suantony.movieappsproject.data.source.remote.response.ReviewResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Flowable<MoviePopularResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Flowable<MovieTopRatedResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Flowable<MovieNowPlayingResponse>

    @GET("movie/{movie_id}/reviews")
    fun getReviewMovie(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Flowable<ReviewResponse>
}