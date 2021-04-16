package com.asadevelopers.findmovies.db

class LikedMovieRepository(private val db: MovieDatabase) {

    suspend fun insert(movie : MovieDetail) = db.getMovieDao().insertMovie(movie)

    suspend fun delete(movie : MovieDetail) = db.getMovieDao().deleteMovie(movie)

    fun getAllMovies() = db.getMovieDao().getAllMovies()

}