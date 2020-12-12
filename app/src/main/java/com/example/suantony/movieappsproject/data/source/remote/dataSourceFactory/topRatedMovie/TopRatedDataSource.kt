package com.example.suantony.movieappsproject.data.source.remote.dataSourceFactory.topRatedMovie

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.suantony.movieappsproject.BuildConfig
import com.example.suantony.movieappsproject.data.source.remote.network.ApiResponse
import com.example.suantony.movieappsproject.data.source.remote.network.ApiService
import com.example.suantony.movieappsproject.data.source.remote.response.MovieTopRatedItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class TopRatedDataSource constructor(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, MovieTopRatedItem>() {

    private val api_key = BuildConfig.API_KEY
    private var page = 1

    companion object {
        val topRateApiResponse = BehaviorSubject.create<ApiResponse>()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieTopRatedItem>
    ) {
        Log.d("testingTRSource", "LoadInitial")
        topRateApiResponse.onNext(ApiResponse.Loading)
        compositeDisposable.add(
            apiService.getTopRatedMovie(api_key, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.results, null, page + 1)
                        topRateApiResponse.onNext(ApiResponse.Success)
                        Log.d("testingTRSource", "LoadInitialSuccess")
                    },
                    {
                        topRateApiResponse.onNext(ApiResponse.Error(it.message))
                        Log.e("testingTRSource", it.message.toString())
                    }
                )
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieTopRatedItem>
    ) {
        Log.d("testingTRSource", "loadAfter")
        topRateApiResponse.onNext(ApiResponse.Loading)
        compositeDisposable.add(
            apiService.getTopRatedMovie(api_key, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.results, params.key + 1)
                            topRateApiResponse.onNext(ApiResponse.Success)
                        } else {
                            topRateApiResponse.onNext(ApiResponse.Error("End of the List"))
                        }
                    },
                    {
                        topRateApiResponse.onNext(ApiResponse.Error(it.message))
                        Log.e("testingTRSource", it.message.toString())
                    }
                )

        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieTopRatedItem>
    ) {
        Log.d("testingTRSource", "LoadBefore")
    }

}
