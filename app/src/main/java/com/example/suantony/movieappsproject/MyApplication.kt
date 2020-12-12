package com.example.suantony.movieappsproject

import android.app.Application
import com.example.suantony.movieappsproject.di.databaseModule
import com.example.suantony.movieappsproject.di.networkModule
import com.example.suantony.movieappsproject.di.repositoryModule
import com.example.suantony.movieappsproject.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule,
                    databaseModule
                )
            )
        }
    }
}