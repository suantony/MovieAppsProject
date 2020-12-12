package com.example.suantony.movieappsproject.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.DataSource
import com.example.suantony.movieappsproject.BuildConfig
import com.example.suantony.movieappsproject.data.source.remote.dataSourceFactory.nowPlayingMovie.NowPlayingDataSource
import com.example.suantony.movieappsproject.data.source.remote.dataSourceFactory.nowPlayingMovie.NowPlayingDataSourceFactory
import com.example.suantony.movieappsproject.data.source.remote.dataSourceFactory.popularMovie.PopularDataSource
import com.example.suantony.movieappsproject.data.source.remote.dataSourceFactory.popularMovie.PopularDataSourceFactory
import com.example.suantony.movieappsproject.data.source.remote.dataSourceFactory.topRatedMovie.TopRatedDataSource
import com.example.suantony.movieappsproject.data.source.remote.dataSourceFactory.topRatedMovie.TopRatedDataSourceFactory
import com.example.suantony.movieappsproject.data.source.remote.network.ApiResponse
import com.example.suantony.movieappsproject.data.source.remote.network.ApiResponseState
import com.example.suantony.movieappsproject.data.source.remote.network.ApiService
import com.example.suantony.movieappsproject.data.source.remote.response.MovieNowPlayingItem
import com.example.suantony.movieappsproject.data.source.remote.response.MoviePopularItem
import com.example.suantony.movieappsproject.data.source.remote.response.MovieTopRatedItem
import com.example.suantony.movieappsproject.data.source.remote.response.ReviewItem
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class RemoteDataSource(private val apiService: ApiService) {

    val compositeDisposablePopularMovie = CompositeDisposable()
    val compositeDisposableNowPlayingMovie = CompositeDisposable()
    val compositeDisposableTopRatedMovie = CompositeDisposable()
    val api_key = BuildConfig.API_KEY

    fun getPopularMovieByPage(): DataSource.Factory<Int, MoviePopularItem> {
        return PopularDataSourceFactory(apiService, compositeDisposablePopularMovie)
    }

    fun getApiResponsePopularMovie(): Flowable<ApiResponse> {
        val resultData = BehaviorSubject.create<ApiResponse>()
        compositeDisposablePopularMovie.add(
            PopularDataSource.popularApiResponse
                .subscribeOn(Schedulers.io())
                .doOnComplete { compositeDisposablePopularMovie.dispose() }
                .subscribe { res ->
                    when (res) {
                        is ApiResponse.Loading -> {
                            Log.d("testingRemoteP", "loading")
                            resultData.onNext(ApiResponse.Loading)
                        }
                        is ApiResponse.Success -> {
                            Log.d("testingRemoteP", "loaded")
                            resultData.onNext(ApiResponse.Success)
                        }
                        is ApiResponse.Error -> {
                            Log.d("testingRemoteP", "error")
                            resultData.onNext(ApiResponse.Error(res.msg))
                        }
                    }
                }
        )
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getTopRatedMovieByPage(): DataSource.Factory<Int, MovieTopRatedItem> {
        return TopRatedDataSourceFactory(apiService, compositeDisposableTopRatedMovie)
    }

    fun getApiResponseTopRatedMovie(): Flowable<ApiResponse> {
        val resultData = BehaviorSubject.create<ApiResponse>()
        compositeDisposableTopRatedMovie.add(
            TopRatedDataSource.topRateApiResponse
                .subscribeOn(Schedulers.io())
                .doOnComplete { compositeDisposableTopRatedMovie.dispose() }
                .subscribe { res ->
                    when (res) {
                        is ApiResponse.Loading -> {
                            Log.d("testingRemoteTP", "loading")
                            resultData.onNext(ApiResponse.Loading)
                        }
                        is ApiResponse.Success -> {
                            Log.d("testingRemoteTP", "loaded")
                            resultData.onNext(ApiResponse.Success)
                        }
                        is ApiResponse.Error -> {
                            Log.d("testingRemoteTP", "error")
                            resultData.onNext(ApiResponse.Error(res.msg))
                        }
                    }
                }
        )
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getNowPlayingMovieByPage(): DataSource.Factory<Int, MovieNowPlayingItem> {
        return NowPlayingDataSourceFactory(apiService, compositeDisposableNowPlayingMovie)
    }

    fun getApiResponseNowPlayingMovie(): Flowable<ApiResponse> {
        val resultData = BehaviorSubject.create<ApiResponse>()
        compositeDisposableNowPlayingMovie.add(
            NowPlayingDataSource.nowPlayingApiResponse
                .subscribeOn(Schedulers.io())
                .doOnComplete { compositeDisposableNowPlayingMovie.dispose() }
                .subscribe { res ->
                    when (res) {
                        is ApiResponse.Loading -> {
                            Log.d("testingRemoteNP", "loading")
                            resultData.onNext(ApiResponse.Loading)
                        }
                        is ApiResponse.Success -> {
                            Log.d("testingRemoteNP", "loaded")
                            resultData.onNext(ApiResponse.Success)
                        }
                        is ApiResponse.Empty -> {
                            Log.d("testingRemoteNP", "loaded")
                            resultData.onNext(ApiResponse.Empty)
                        }
                        is ApiResponse.Error -> {
                            Log.d("testingRemoteNP", "error")
                            resultData.onNext(ApiResponse.Error(res.msg))
                        }
                    }
                }
        )
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getReviewMovieByPage(
        movieId: String,
        page: Int
    ): Flowable<ApiResponseState<List<ReviewItem>>> {
        val resultData = BehaviorSubject.create<ApiResponseState<List<ReviewItem>>>()
        resultData.onNext(ApiResponseState.Loading)

        apiService.getReviewMovie(movieId, api_key, page)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    if (it.results.isNotEmpty()) {
                        Log.d("testingRemoteR", "isnotempty")
                        if (it.totalPages >= page) {

                            resultData.onNext(ApiResponseState.Success(it.results))
                            Log.d("testingRemoteR", "success")
                        } else {
                            resultData.onNext(ApiResponseState.Error("endofthelist"))
                            Log.d("testingRemoteR", "endofthelist")
                        }
                    } else {
                        resultData.onNext(ApiResponseState.Empty)
                        Log.d("testingRemoteR", "empty")
                    }
                },
                {
                    resultData.onNext(ApiResponseState.Error(it.message))
                    Log.e("testingRemoteR", it.message.toString())
                }
            )

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

}