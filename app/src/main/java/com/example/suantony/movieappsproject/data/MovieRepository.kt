package com.example.suantony.movieappsproject.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.suantony.movieappsproject.data.source.local.LocalDataSource
import com.example.suantony.movieappsproject.data.source.local.entity.MovieEntity
import com.example.suantony.movieappsproject.data.source.remote.RemoteDataSource
import com.example.suantony.movieappsproject.data.source.remote.network.ApiResponse
import com.example.suantony.movieappsproject.data.source.remote.network.ApiResponseState
import com.example.suantony.movieappsproject.data.source.remote.response.MovieNowPlayingItem
import com.example.suantony.movieappsproject.data.source.remote.response.MoviePopularItem
import com.example.suantony.movieappsproject.data.source.remote.response.MovieTopRatedItem
import com.example.suantony.movieappsproject.data.source.remote.response.ReviewItem
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieDataResource {

    override fun getPopularMovie(): LiveData<Resource<PagedList<MoviePopularItem>>> {
        val result = MediatorLiveData<Resource<PagedList<MoviePopularItem>>>()
        val compositeDisposable = CompositeDisposable()

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        val livePagedList =
            LivePagedListBuilder(remoteDataSource.getPopularMovieByPage(), config).build()

        result.addSource(livePagedList) { pagedList ->
            compositeDisposable.add(
                remoteDataSource.getApiResponsePopularMovie()
                    .subscribeOn(Schedulers.io())
                    .doOnComplete { compositeDisposable.dispose() }
                    .subscribe { res ->
                        when (res) {
                            is ApiResponse.Loading -> {
                                Log.d("testingRepoP", "loading")
                                result.postValue(Resource.Loading(null))
                            }
                            is ApiResponse.Success -> {
                                Log.d("testingRepoP", "loaded")
                                result.postValue(Resource.Success(pagedList))
                            }
                            is ApiResponse.Error -> {
                                Log.d("testingRepoP", "error")
                                result.postValue(Resource.Error(res.msg, null))
                            }

                        }
                    }
            )
        }
        return result
    }

    override fun getTopRatedMovie(): LiveData<Resource<PagedList<MovieTopRatedItem>>> {
        val result = MediatorLiveData<Resource<PagedList<MovieTopRatedItem>>>()
        val compositeDisposable = CompositeDisposable()

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        val livePagedList =
            LivePagedListBuilder(remoteDataSource.getTopRatedMovieByPage(), config).build()

        result.addSource(livePagedList) { pagedList ->
            compositeDisposable.add(
                remoteDataSource.getApiResponseTopRatedMovie()
                    .subscribeOn(Schedulers.io())
                    .doOnComplete { compositeDisposable.dispose() }
                    .subscribe { res ->
                        when (res) {
                            is ApiResponse.Loading -> {
                                Log.d("testingRepoTR", "loading")
                                result.postValue(Resource.Loading(null))
                            }
                            is ApiResponse.Success -> {
                                Log.d("testingRepoTR", "loaded")
                                result.postValue(Resource.Success(pagedList))
                            }
                            is ApiResponse.Error -> {
                                Log.d("testingRepoTR", "error")
                                result.postValue(Resource.Error(res.msg, null))
                            }

                        }
                    }
            )
        }
        return result
    }

    override fun getNowPlayingMovie(): LiveData<Resource<PagedList<MovieNowPlayingItem>>> {
        val result = MediatorLiveData<Resource<PagedList<MovieNowPlayingItem>>>()
        val compositeDisposable = CompositeDisposable()

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        val livePagedList =
            LivePagedListBuilder(remoteDataSource.getNowPlayingMovieByPage(), config).build()

        result.addSource(livePagedList) { pagedList ->
            compositeDisposable.add(
                remoteDataSource.getApiResponseNowPlayingMovie()
                    .subscribeOn(Schedulers.io())
                    .doOnComplete { compositeDisposable.dispose() }
                    .subscribe { res ->
                        when (res) {
                            is ApiResponse.Loading -> {
                                Log.d("testingRepoNP", "loading")
                                result.postValue(Resource.Loading(null))
                            }
                            is ApiResponse.Success -> {
                                Log.d("testingRepoNP", "loaded")
                                result.postValue(Resource.Success(pagedList))
                            }
                            is ApiResponse.Empty -> {
                                Log.d("testingRepoNP", "loaded")
                                result.postValue(Resource.Empty(null))
                            }
                            is ApiResponse.Error -> {
                                Log.d("testingRepoNP", "error")
                                result.postValue(Resource.Error(res.msg, null))
                            }

                        }
                    }
            )
        }
        return result
    }

//    override fun getReviewMovie(movieId: String): LiveData<Resource<PagedList<ReviewItem>>> {
//        val result = MediatorLiveData<Resource<PagedList<ReviewItem>>>()
//        val compositeDisposable = CompositeDisposable()
//
//        val config = PagedList.Config.Builder()
//            .setEnablePlaceholders(false)
//            .setPageSize(5)
//            .build()
//
//        val livePagedList =
//            LivePagedListBuilder(remoteDataSource.getReviewMovieByPage(movieId), config).build()
//
//        result.addSource(livePagedList) { pagedList ->
//            compositeDisposable.add(
//                remoteDataSource.getApiResponseReviewMovie()
//                    .subscribeOn(Schedulers.io())
//                    .doOnComplete { compositeDisposable.dispose() }
//                    .subscribe { res ->
//                        when (res) {
//                            is ApiResponse.Loading -> {
//                                Log.d("testingRepoR", "loading")
//                                result.postValue(Resource.Loading(null))
//                            }
//                            is ApiResponse.Success -> {
//                                Log.d("testingRepoR", "loaded")
//                                result.postValue(Resource.Success(pagedList))
//                            }
//                            is ApiResponse.Error -> {
//                                Log.d("testingRepoR", "error")
//                                result.postValue(Resource.Error(res.msg, null))
//                            }
//
//                        }
//                    }
//            )
//        }
//        return result
//    }

    override fun getReviewMovie(movieId: String, page: Int): LiveData<Resource<List<ReviewItem>>> {
        val result = MediatorLiveData<Resource<List<ReviewItem>>>()
        val compositeDisposable = CompositeDisposable()

        compositeDisposable.add(
            remoteDataSource.getReviewMovieByPage(movieId, page)
                .subscribeOn(Schedulers.io())
                .doOnComplete { compositeDisposable.dispose() }
                .subscribe { res ->
                    when (res) {
                        is ApiResponseState.Loading -> {
                            Log.d("testingRepoR", "loading")
                            result.postValue(Resource.Loading(null))
                        }
                        is ApiResponseState.Success -> {
                            Log.d("testingRepoR", "loaded")
                            result.postValue(Resource.Success(res.data))
                        }
                        is ApiResponseState.Empty -> {
                            Log.d("testingRepoR", "empty")
                            result.postValue(Resource.Empty(null))
                        }
                        is ApiResponseState.Error -> {
                            Log.d("testingRepoR", "error")
                            result.postValue(Resource.Error(res.msg, null))
                        }

                    }
                }
        )

        return result
    }

    override fun getAllFavoriteMovie(): Flowable<List<MovieEntity>> {
        return localDataSource.getAllFavoriteMovie()
    }

    override fun getFavoriteMovieById(movieId: String): Flowable<MovieEntity> {
        return localDataSource.getFavoriteMovieById(movieId)
    }

    override fun insertFavoriteMovie(movie: MovieEntity) {
        localDataSource.insertFavoriteMovie(movie)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun deleteFavoriteMovie(movie: MovieEntity) {
        localDataSource.deleteFavoriteMovie(movie)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }


}