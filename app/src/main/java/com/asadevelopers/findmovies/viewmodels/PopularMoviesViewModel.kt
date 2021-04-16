package com.asadevelopers.findmovies.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asadevelopers.findmovies.models.Movie

class PopularMoviesViewModel: ViewModel() {

    var list = MutableLiveData<ArrayList<Movie>>()
    var newList = arrayListOf<Movie>()

    fun addMovie(movie: Movie){
        newList.add(movie)
        list.value = newList
    }
}