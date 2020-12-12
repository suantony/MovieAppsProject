package com.example.suantony.movieappsproject.data.source.remote.dataSourceFactory.popularMovie

import android.util.Log
import androidx.paging.DataSource
import com.example.suantony.movieappsproject.data.source.remote.network.ApiService
import com.example.suantony.movieappsproject.data.source.remote.response.MoviePopularItem
import io.reactivex.disposables.CompositeDisposable

class PopularDataSourceFactory(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MoviePopularItem>() {

    override fun create(): DataSource<Int, MoviePopularItem> {
        Log.d("testingPFactory", "PopularDataSourceFactory")
        val popularDataSource = PopularDataSource(apiService, compositeDisposable)
        return popularDataSource
    }
}