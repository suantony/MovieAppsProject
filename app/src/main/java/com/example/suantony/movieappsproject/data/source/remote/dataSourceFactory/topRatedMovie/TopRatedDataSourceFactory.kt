package com.example.suantony.movieappsproject.data.source.remote.dataSourceFactory.topRatedMovie

import android.util.Log
import androidx.paging.DataSource
import com.example.suantony.movieappsproject.data.source.remote.network.ApiService
import com.example.suantony.movieappsproject.data.source.remote.response.MovieTopRatedItem
import io.reactivex.disposables.CompositeDisposable

class TopRatedDataSourceFactory(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MovieTopRatedItem>() {

    override fun create(): DataSource<Int, MovieTopRatedItem> {
        Log.d("testingTRFactory", "TopRatedDataSourceFactory")
        val topRatedDataSource = TopRatedDataSource(apiService, compositeDisposable)
        return topRatedDataSource
    }
}