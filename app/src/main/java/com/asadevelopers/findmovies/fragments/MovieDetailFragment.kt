package com.asadevelopers.findmovies.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.asadevelopers.findmovies.MainActivity
import com.asadevelopers.findmovies.MySingleton
import com.asadevelopers.findmovies.R
import com.asadevelopers.findmovies.adapters.MoviesAdapter
import com.asadevelopers.findmovies.adapters.OnMoviesClicked
import com.asadevelopers.findmovies.databinding.FragmentMovieDetailBinding
import com.asadevelopers.findmovies.db.LikedMovieViewModel
import com.asadevelopers.findmovies.db.MovieDetail
import com.asadevelopers.findmovies.models.Movie
import com.bumptech.glide.Glide

class MovieDetailFragment : Fragment(), OnMoviesClicked, RatingBar.OnRatingBarChangeListener {

    private var rating1: Float = 0.0f
    private lateinit var binding: FragmentMovieDetailBinding

    private var imageUrl = "https://image.tmdb.org/t/p/w500"

    private lateinit var moviesAdapter: MoviesAdapter

    lateinit var activity1: MainActivity
    lateinit var movieDetail: MovieDetail
    lateinit var movie: MovieDetail

    private lateinit var model: LikedMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)
        activity1 = activity as MainActivity
        val view = binding.root
        model = activity?.run {
            ViewModelProviders.of(this)[LikedMovieViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        setHasOptionsMenu(true)


        movie = arguments?.get("movie") as MovieDetail
        Log.i("Movie", movie.toString())
        imageUrl += movie.movieImage

        binding.toolbar.inflateMenu(R.menu.like_menu)
        binding.toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }

        getMovieLength(movie.id)
        binding.tvMovieTitle.text = movie.movietitle
        Glide.with(view.context).load(imageUrl).into(binding.ivMovieImage)
        rating1 = movie.rating!!.toFloat()
        binding.tvRating.text = movie.rating
        binding.tvLanguage.text = movie.language
        binding.tvStoryLine.text = movie.storyLine
        binding.tvYear.text = movie.releasedDate

        binding.ratingBar.rating = rating1
        getSimilarMovies(movie.id)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.lkeMovie -> {
                if (!movie.liked) {
                    movie.liked = true
                    movieDetail = MovieDetail(
                        movie.id,
                        movie.movietitle,
                        movie.movieImage,
                        movie.runtime,
                        movie.storyLine,
                        movie.releasedDate,
                        movie.rating,
                        movie.language,
                        movie.liked
                    )
                    model.insert(movieDetail)
                    Toast.makeText(activity, "Added to liked Movies", Toast.LENGTH_SHORT).show()
                    item.setIcon(R.drawable.ic_favorite)
                } else {
                    movieDetail = MovieDetail(
                        movie.id,
                        movie.movietitle,
                        movie.movieImage,
                        movie.runtime,
                        movie.storyLine,
                        movie.releasedDate,
                        movie.rating,
                        movie.language,
                        movie.liked
                    )
                    model.delete(movieDetail)
                    movie.liked = false
                    movieDetail.liked = false
                    item.setIcon(R.drawable.ic_favorite)
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }


    private fun getSimilarMovies(id: Int) {
        val similarMoviesUrl =
            "https://api.themoviedb.org/3/movie/$id/similar?api_key=2ca504baeb0a1f37b6398b74a4bff058&language=en-US&page=1"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, similarMoviesUrl, null,
            { response ->
                Log.i("Response", response.toString())
                val similarMoviesArray = response.getJSONArray("results")
                val similarMoviesList = ArrayList<MovieDetail>()
                for (i in 0 until similarMoviesArray.length()) {
                    val jsonObject = similarMoviesArray.getJSONObject(i)
                    val movie = MovieDetail(
                        jsonObject.getInt("id"),
                        jsonObject.getString("title"),
                        jsonObject.getString("poster_path"),
                        "",
                        jsonObject.getString("overview"),
                        jsonObject.getString("release_date"),
                        jsonObject.getString("vote_average"),
                        jsonObject.getString("original_language")
                    )
                    Log.i("Movie", movie.toString())
                    similarMoviesList.add(movie)
                }
                moviesAdapter = MoviesAdapter(similarMoviesList, this)
                binding.rvSimilarMovies.adapter = moviesAdapter
            },
            { error ->

            })

        MySingleton.getInstance(context!!).addToRequestQueue(jsonObjectRequest)

    }

    private fun getMovieLength(id: Int) {
        val url =
            "https://api.themoviedb.org/3/movie/$id?api_key=2ca504baeb0a1f37b6398b74a4bff058&language=en-US"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                Log.i("RunTime", response.getString("runtime"))
                val runtime = response.getString("runtime")
                convertRunTime(runtime)
            },
            { error ->
                Log.i("error", error.message.toString())

            })


        MySingleton.getInstance(context!!).addToRequestQueue(jsonObjectRequest)


    }

    private fun convertRunTime(runtime: String) {
        val time = runtime.toInt()
        val hour = time / 60
        var minute1 = ""
        var minute = time - (hour * 60)
        val count = minute.toString()
        val c = count.length
        Log.i("c", c.toString())
        minute1 = if (c < 2) {
            "0$count"
        } else {
            count
        }

        val runTime = "$hour:$minute1:00"
        binding.tvLength.text = runTime

    }

    override fun onItemClicked(movie: MovieDetail) {

        val fragment = MovieDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)
        fragment.arguments = bundle
        (activity as MainActivity).setCurrentFragment(fragment)

    }

    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
        ratingBar?.rating = rating1

    }


}

