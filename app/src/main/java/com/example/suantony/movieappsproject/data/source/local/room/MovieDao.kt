package com.example.suantony.movieappsproject.data.source.local.room

import androidx.room.*
import com.example.suantony.movieappsproject.data.source.local.entity.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MovieDao {
    @Query("SELECT * FROM movieEntity")
    fun getAllFavoriteMovie(): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movieEntity WHERE movieId LIKE :movieId ")
    fun getFavoriteMovieById(movieId:String):Flowable<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: MovieEntity): Completable

    @Delete
    fun deleteFavoriteMovie(movie: MovieEntity): Completable


}