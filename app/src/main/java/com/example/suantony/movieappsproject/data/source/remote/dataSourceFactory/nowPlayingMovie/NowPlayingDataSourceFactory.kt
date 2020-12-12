package com.example.suantony.movieappsproject.data.source.remote.dataSourceFactory.nowPlayingMovie

import android.util.Log
import androidx.paging.DataSource
import com.example.suantony.movieappsproject.data.source.remote.network.ApiService
import com.example.suantony.movieappsproject.data.source.remote.response.MovieNowPlayingItem
import io.reactivex.disposables.CompositeDisposable

class NowPlayingDataSourceFactory(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MovieNowPlayingItem>() {

    override fun create(): DataSource<Int, MovieNowPlayingItem> {
        Log.d("testingNPFactory", "NowPlayingDataSourceFactory")
        val nowPlayingDataSource = NowPlayingDataSource(apiService, compositeDisposable)
        return nowPlayingDataSource
    }
}