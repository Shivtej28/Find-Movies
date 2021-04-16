package com.asadevelopers.findmovies.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.asadevelopers.findmovies.R
import com.asadevelopers.findmovies.db.MovieDetail
import com.asadevelopers.findmovies.fragments.HomeFragment
import com.asadevelopers.findmovies.models.Movie
import com.bumptech.glide.Glide

class PopularMoviesAdapter(private val moviesList : ArrayList<MovieDetail>, private val listener : PopularMoviesClicked
    ) : RecyclerView.Adapter<PopularMoviesAdapter.ViewHolder>() {


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val tvMovieName : TextView = itemView.findViewById(R.id.tvMovieName)
            val ivMovieBanner : ImageView = itemView.findViewById(R.id.ivMovieBanner)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_movies_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = moviesList[position]
        val imageUrl = "https://image.tmdb.org/t/p/w500"+currentItem.movieImage
        holder.tvMovieName.text = currentItem.movietitle
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.ivMovieBanner)
        holder.itemView.setOnClickListener {
            listener.onItemClicked(currentItem)
        }

    }

    fun updatePopularMovies(updatedMovies : ArrayList<MovieDetail>){
        moviesList.clear()
        moviesList.addAll(updatedMovies)
        Log.i("List", moviesList.toString())

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = moviesList.size
}

interface PopularMoviesClicked{
    fun onItemClicked(movie: MovieDetail)
}