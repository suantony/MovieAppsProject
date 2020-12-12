package com.example.suantony.movieappsproject.data.source.remote.dataSourceFactory.nowPlayingMovie

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.suantony.movieappsproject.BuildConfig
import com.example.suantony.movieappsproject.data.source.remote.network.ApiResponse
import com.example.suantony.movieappsproject.data.source.remote.network.ApiService
import com.example.suantony.movieappsproject.data.source.remote.response.MovieNowPlayingItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class NowPlayingDataSource constructor(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, MovieNowPlayingItem>() {

    private val api_key = BuildConfig.API_KEY
    private var page = 1

    companion object {
        val nowPlayingApiResponse = BehaviorSubject.create<ApiResponse>()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieNowPlayingItem>
    ) {
        Log.d("testingNPSource", "LoadInitial")
        nowPlayingApiResponse.onNext(ApiResponse.Loading)
        compositeDisposable.add(
            apiService.getNowPlayingMovie(api_key, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.results, null, page + 1)
                        nowPlayingApiResponse.onNext(ApiResponse.Success)
                        Log.d("testingNPSource", "LoadInitialSuccess")
                    },
                    {
                        nowPlayingApiResponse.onNext(ApiResponse.Error(it.message))
                        Log.e("testingNPSource", it.message.toString())
                    }
                )
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieNowPlayingItem>
    ) {
        Log.d("testingNPSource", "loadAfter")
        nowPlayingApiResponse.onNext(ApiResponse.Loading)
        compositeDisposable.add(
            apiService.getNowPlayingMovie(api_key, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.results, params.key + 1)
                            nowPlayingApiResponse.onNext(ApiResponse.Success)
                        } else {
                            nowPlayingApiResponse.onNext(ApiResponse.Error("End of the List"))
                        }
                    },
                    {
                        nowPlayingApiResponse.onNext(ApiResponse.Error(it.message))
                        Log.e("testingNPSource", it.message.toString())
                    }
                )

        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieNowPlayingItem>
    ) {
        Log.d("testingNPSource", "LoadBefore")
    }

}
