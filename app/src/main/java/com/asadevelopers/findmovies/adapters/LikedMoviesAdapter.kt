package com.asadevelopers.findmovies.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asadevelopers.findmovies.MainActivity
import com.asadevelopers.findmovies.R
import com.asadevelopers.findmovies.db.LikedMovieViewModel
import com.asadevelopers.findmovies.db.MovieDetail
import com.asadevelopers.findmovies.fragments.MovieDetailFragment
import com.asadevelopers.findmovies.models.Movie
import com.bumptech.glide.Glide

class LikedMoviesAdapter(
    var list: List<MovieDetail>,
    val listener: OnDeleteItem,
    val clicked: onClickedItem
): RecyclerView.Adapter<LikedMoviesAdapter.ViewHolder>()  {

        inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
            val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
            val tvRating : TextView = itemView.findViewById(R.id.tvrating)
            val ivImage : ImageView = itemView.findViewById(R.id.bannerImg)
            val likedBtn: ImageView = itemView.findViewById(R.id.likedBtn)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.liked_movies, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        val imageUrl = "https://image.tmdb.org/t/p/w500"+currentItem.movieImage
        holder.tvTitle.text = currentItem.movietitle
        holder.tvRating.text = currentItem.rating
        holder.likedBtn.setOnClickListener {
           listener.onItemClicked(currentItem)
        }
        holder.itemView.setOnClickListener {
           clicked.onClicked(currentItem)

        }
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.ivImage)
    }

    override fun getItemCount(): Int = list.size

}

interface OnDeleteItem{
    fun onItemClicked(movieDetail: MovieDetail)
}

interface onClickedItem{
    fun onClicked(movieDetail: MovieDetail)
}