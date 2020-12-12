package com.example.suantony.movieappsproject.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.suantony.movieappsproject.data.MovieRepository
import com.example.suantony.movieappsproject.data.Resource
import com.example.suantony.movieappsproject.data.source.remote.response.MovieNowPlayingItem
import com.example.suantony.movieappsproject.data.source.remote.response.MoviePopularItem
import com.example.suantony.movieappsproject.data.source.remote.response.MovieTopRatedItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var popularMovieObserver: Observer<Resource<PagedList<MoviePopularItem>>>

    @Mock
    private lateinit var topRatedMovieObserver: Observer<Resource<PagedList<MovieTopRatedItem>>>

    @Mock
    private lateinit var nowPlayingMovieObserver: Observer<Resource<PagedList<MovieNowPlayingItem>>>

    @Mock
    private lateinit var popularMoviePagedList: PagedList<MoviePopularItem>

    @Mock
    private lateinit var topRatedMoviePagedList: PagedList<MovieTopRatedItem>

    @Mock
    private lateinit var nowPlayingPagedList: PagedList<MovieNowPlayingItem>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun getPopularMovie() {
        val dummyPopularMovie = Resource.Success(popularMoviePagedList)
        Mockito.`when`(dummyPopularMovie.data?.size).thenReturn(20)
        val popularMovie = MutableLiveData<Resource<PagedList<MoviePopularItem>>>()
        popularMovie.value = dummyPopularMovie

        Mockito.`when`(movieRepository.getPopularMovie()).thenReturn(popularMovie)
        val popularMovieViewModel = viewModel.getPopularMovie().value?.data
        verify<MovieRepository>(movieRepository).getPopularMovie()
        assertNotNull(popularMovieViewModel)
        assertEquals(20, popularMovieViewModel?.size)

        viewModel.getPopularMovie().observeForever(popularMovieObserver)
        verify(popularMovieObserver).onChanged(dummyPopularMovie)
    }

    @Test
    fun getTopRatedMovie() {
        val dummyTopRatedMovie = Resource.Success(topRatedMoviePagedList)
        Mockito.`when`(dummyTopRatedMovie.data?.size).thenReturn(20)
        val topRatedMovie = MutableLiveData<Resource<PagedList<MovieTopRatedItem>>>()
        topRatedMovie.value = dummyTopRatedMovie

        Mockito.`when`(movieRepository.getTopRatedMovie()).thenReturn(topRatedMovie)
        val topRatedMovieViewModel = viewModel.getTopRatedMovie().value?.data
        verify<MovieRepository>(movieRepository).getTopRatedMovie()
        assertNotNull(topRatedMovieViewModel)
        assertEquals(20, topRatedMovieViewModel?.size)

        viewModel.getTopRatedMovie().observeForever(topRatedMovieObserver)
        verify(topRatedMovieObserver).onChanged(dummyTopRatedMovie)
    }

    @Test
    fun getNowPlayingMovie() {
        val dummyNowPlayingMovie = Resource.Success(nowPlayingPagedList)
        Mockito.`when`(dummyNowPlayingMovie.data?.size).thenReturn(20)
        val nowPlayingMovie = MutableLiveData<Resource<PagedList<MovieNowPlayingItem>>>()
        nowPlayingMovie.value = dummyNowPlayingMovie

        Mockito.`when`(movieRepository.getNowPlayingMovie()).thenReturn(nowPlayingMovie)
        val nowPlayingMovieViewModel = viewModel.getNowPlayingMovie().value?.data
        verify<MovieRepository>(movieRepository).getNowPlayingMovie()
        assertNotNull(nowPlayingMovieViewModel)
        assertEquals(20, nowPlayingMovieViewModel?.size)

        viewModel.getNowPlayingMovie().observeForever(nowPlayingMovieObserver)
        verify(nowPlayingMovieObserver).onChanged(dummyNowPlayingMovie)
    }
}