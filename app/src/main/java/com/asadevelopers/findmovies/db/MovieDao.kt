package com.asadevelopers.findmovies.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao   {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieDetail: MovieDetail)

    @Delete
    suspend fun deleteMovie(movieDetail: MovieDetail)

    @Query("SELECT * FROM liked_movie_table")
    fun getAllMovies() : LiveData<List<MovieDetail>>
}