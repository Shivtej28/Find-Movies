package com.asadevelopers.findmovies.db

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LikedMovieViewModel(private val repository: LikedMovieRepository): ViewModel() {

    fun insert(movieDetail: MovieDetail) = CoroutineScope(Dispatchers.Main).launch {
        repository.insert(movieDetail)
    }

    fun delete(movieDetail: MovieDetail) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(movieDetail)
    }

    fun getAllMovies() = repository.getAllMovies()


}