package com.asadevelopers.findmovies.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.asadevelopers.findmovies.MainActivity
import com.asadevelopers.findmovies.MySingleton
import com.asadevelopers.findmovies.R
import com.asadevelopers.findmovies.adapters.MoviesAdapter
import com.asadevelopers.findmovies.adapters.OnMoviesClicked
import com.asadevelopers.findmovies.adapters.PopularMoviesAdapter
import com.asadevelopers.findmovies.databinding.FragmentSearchBinding
import com.asadevelopers.findmovies.db.MovieDetail


class SearchFragment : Fragment(), OnMoviesClicked {


    private lateinit var binding : FragmentSearchBinding
    private lateinit var adapter: MoviesAdapter
    private val SEARCH_MOVIES_URL =
        "https://api.themoviedb.org/3/search/movie?api_key=2ca504baeb0a1f37b6398b74a4bff058&language=en-US&query="

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        binding.rvSearchMovie.layoutManager = GridLayoutManager(context, 2)
        binding.etSearchMovie.addTextChangedListener {
            getSearchMovies(it.toString())
        }
        return view
    }

    private fun getSearchMovies(textToSearch : String){
        val url = SEARCH_MOVIES_URL+textToSearch
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.i("Response", response.toString())
                val searchMoviesArray = response.getJSONArray("results")
                val searchMoviesList = ArrayList<MovieDetail>()
                for (i in 0 until searchMoviesArray.length()) {
                    val jsonObject = searchMoviesArray.getJSONObject(i)
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
                    searchMoviesList.add(movie)
                }
                val adapter = MoviesAdapter(searchMoviesList, this)
                binding.rvSearchMovie.adapter = adapter
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