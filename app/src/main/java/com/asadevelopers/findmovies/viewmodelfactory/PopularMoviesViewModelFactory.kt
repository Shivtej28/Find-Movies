package com.asadevelopers.findmovies.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asadevelopers.findmovies.viewmodels.PopularMoviesViewModel
import java.lang.IllegalArgumentException

class PopularMoviesViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PopularMoviesViewModel::class.java)){
            return PopularMoviesViewModel() as T
        }
        throw IllegalArgumentException ("Unknown")

    }

}