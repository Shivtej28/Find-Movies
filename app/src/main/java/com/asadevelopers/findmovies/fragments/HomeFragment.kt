package com.asadevelopers.findmovies.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.asadevelopers.findmovies.MainActivity
import com.asadevelopers.findmovies.MySingleton
import com.asadevelopers.findmovies.adapters.MoviesAdapter
import com.asadevelopers.findmovies.adapters.OnMoviesClicked
import com.asadevelopers.findmovies.adapters.PopularMoviesAdapter
import com.asadevelopers.findmovies.adapters.PopularMoviesClicked
import com.asadevelopers.findmovies.databinding.FragmentHomeBinding
import com.asadevelopers.findmovies.db.MovieDetail
import com.asadevelopers.findmovies.models.Movie


class HomeFragment : Fragment(), PopularMoviesClicked, OnMoviesClicked {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var popularMoviesList: ArrayList<Movie>

    private val POPULAR_MOVIES_URL =
        "https://api.themoviedb.org/3/movie/popular?api_key=2ca504baeb0a1f37b6398b74a4bff058&language=en-US&page=2"
    private val NEW_RELEASES_MOVIES_URL =
        "https://api.themoviedb.org/3/movie/now_playing?api_key=2ca504baeb0a1f37b6398b74a4bff058&language=en-US"
    private val TOP_RATED_MOVIES_URL =
        "https://api.themoviedb.org/3/movie/top_rated?api_key=2ca504baeb0a1f37b6398b74a4bff058&language=en-US"
    private var CATEGORIES_MOVIES_URL =
        "https://api.themoviedb.org/3/discover/movie?api_key=2ca504baeb0a1f37b6398b74a4bff058&include_adult=false&with_genres="

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        popularMoviesList = ArrayList()

        getPopularMovies(POPULAR_MOVIES_URL, binding.rvPopularMovies)
        getMovies(NEW_RELEASES_MOVIES_URL, binding.rvNewReleases)
        getMovies(TOP_RATED_MOVIES_URL, binding.rvTopRated)

        binding.btnAction.setOnClickListener {
            CATEGORIES_MOVIES_URL += "28"
            openCategoryFragment(CATEGORIES_MOVIES_URL, binding.btnAction.text.toString())
        }
        binding.btnAdventure.setOnClickListener {
            CATEGORIES_MOVIES_URL += "12"
            openCategoryFragment(CATEGORIES_MOVIES_URL, binding.btnAdventure.text.toString())
        }
        binding.btnFantasy.setOnClickListener {
            CATEGORIES_MOVIES_URL += "14"
            openCategoryFragment(CATEGORIES_MOVIES_URL, binding.btnFantasy.text.toString())
        }
        binding.btnScifi.setOnClickListener {
            CATEGORIES_MOVIES_URL += "878"
            openCategoryFragment(CATEGORIES_MOVIES_URL, binding.btnScifi.text.toString())
        }

        binding.tvAllCategories.setOnClickListener {
            (activity as MainActivity).setCurrentFragment(AllCategoryFragment())
        }

        binding.tvNewReleases.setOnClickListener {
            openCategoryFragment(NEW_RELEASES_MOVIES_URL, "New Releases")
        }

        binding.tvTopRated.setOnClickListener {
            openCategoryFragment(TOP_RATED_MOVIES_URL, "Top Rated")
        }


        return view
    }

    private fun openCategoryFragment(url: String, text: String) {
        val fragment = CategoryFragment()
        val bundle = Bundle()
        bundle.putString("categoryId", url)
        bundle.putString("Name", text)
        fragment.arguments = bundle
        (activity as MainActivity).setCurrentFragment(fragment)
    }

    private fun getPopularMovies(url: String, recyclerView: RecyclerView) {
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                Log.i("Response", response.toString())
                val topRatedMoviesArray = response.getJSONArray("results")
                val topRatedMoviesList = ArrayList<MovieDetail>()
                for (i in 0 until 6) {
                    val jsonObject = topRatedMoviesArray.getJSONObject(i)
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
                    topRatedMoviesList.add(movie)
                }
                val adapter = PopularMoviesAdapter(topRatedMoviesList, this)
                recyclerView.adapter = adapter

            },
            { error ->

            })
        MySingleton.getInstance(context!!).addToRequestQueue(jsonObjectRequest)
    }

    private fun getMovies(url: String, recyclerView: RecyclerView) {
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                Log.i("Response", response.toString())
                val topRatedMoviesArray = response.getJSONArray("results")
                val topRatedMoviesList = ArrayList<MovieDetail>()
                for (i in 0 until 6) {
                    val jsonObject = topRatedMoviesArray.getJSONObject(i)
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
                    topRatedMoviesList.add(movie)
                }
                val adapter = MoviesAdapter(topRatedMoviesList, this)
                recyclerView.adapter = adapter

            },
            { error ->

            })
        MySingleton.getInstance(context!!).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(movie: MovieDetail) {

        val fragment = MovieDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)
        fragment.arguments = bundle
        (activity as MainActivity).setCurrentFragment(fragment)

    }


}