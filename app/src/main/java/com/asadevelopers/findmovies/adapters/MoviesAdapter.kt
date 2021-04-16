package com.asadevelopers.findmovies.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asadevelopers.findmovies.R
import com.asadevelopers.findmovies.db.MovieDetail
import com.asadevelopers.findmovies.models.Movie
import com.bumptech.glide.Glide

class MoviesAdapter(
    private val list : List<MovieDetail>, private val listener: OnMoviesClicked
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvMovieName : TextView = itemView.findViewById(R.id.tvMovieName)
        val ivMovieBanner : ImageView = itemView.findViewById(R.id.ivMovieBanner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        Log.i("Current", currentItem.toString())
        holder.tvMovieName.text = currentItem.movietitle
        val imageUrl = "https://image.tmdb.org/t/p/w500"+currentItem.movieImage
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.ivMovieBanner)
        holder.itemView.setOnClickListener {
            listener.onItemClicked(currentItem)
        }


    }

    override fun getItemCount(): Int = list.size
}

interface OnMoviesClicked{
    fun onItemClicked(movie: MovieDetail)
}