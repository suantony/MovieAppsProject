package com.example.suantony.movieappsproject.data.source.remote.dataSourceFactory.popularMovie

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.suantony.movieappsproject.BuildConfig
import com.example.suantony.movieappsproject.data.source.remote.network.ApiResponse
import com.example.suantony.movieappsproject.data.source.remote.network.ApiService
import com.example.suantony.movieappsproject.data.source.remote.response.MoviePopularItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class PopularDataSource constructor(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, MoviePopularItem>() {

    private val api_key = BuildConfig.API_KEY
    private var page = 1

    companion object {
        val popularApiResponse = BehaviorSubject.create<ApiResponse>()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MoviePopularItem>
    ) {
        Log.d("testingPSource", "LoadInitial")
        popularApiResponse.onNext(ApiResponse.Loading)
        compositeDisposable.add(
            apiService.getPopularMovie(api_key, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.results, null, page + 1)
                        popularApiResponse.onNext(ApiResponse.Success)
                        Log.d("testingPSource", "LoadInitialSuccess")
                    },
                    {
                        popularApiResponse.onNext(ApiResponse.Error(it.message))
                        Log.e("testingPSource", it.message.toString())
                    }
                )
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MoviePopularItem>
    ) {
        Log.d("testingPSource", "loadAfter")
        popularApiResponse.onNext(ApiResponse.Loading)
        compositeDisposable.add(
            apiService.getPopularMovie(api_key, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.results, params.key + 1)
                            popularApiResponse.onNext(ApiResponse.Success)
                        } else {
                            popularApiResponse.onNext(ApiResponse.Error("End of the List"))
                        }
                    },
                    {
                        popularApiResponse.onNext(ApiResponse.Error(it.message))
                        Log.e("testingPSource", it.message.toString())
                    }
                )

        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MoviePopularItem>
    ) {
        Log.d("testingPSource", "LoadBefore")
    }

}
