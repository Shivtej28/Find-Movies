package com.asadevelopers.findmovies.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.asadevelopers.findmovies.MainActivity
import com.asadevelopers.findmovies.MySingleton
import com.asadevelopers.findmovies.adapters.MoviesAdapter
import com.asadevelopers.findmovies.adapters.OnMoviesClicked
import com.asadevelopers.findmovies.databinding.FragmentCategoryBinding
import com.asadevelopers.findmovies.db.MovieDetail
import com.asadevelopers.findmovies.models.Movie

class CategoryFragment : Fragment(), OnMoviesClicked {

    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        val url = arguments?.get("categoryId").toString()
        val name = arguments?.get("Name")
        binding.tvName.text = name.toString()
        binding.rvCategoryMovies.layoutManager = GridLayoutManager(context, 2)
        getMovies(url, binding.rvCategoryMovies)
        return view
    }

    private fun getMovies(categoryMoviesUrl: String, rvCategoryMovies: RecyclerView) {

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, categoryMoviesUrl, null,
            { response ->
                Log.i("Response", response.toString())
                val topRatedMoviesArray = response.getJSONArray("results")
                val topRatedMoviesList = ArrayList<MovieDetail>()
                for (i in 0 until topRatedMoviesArray.length()) {
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
                rvCategoryMovies.adapter = adapter

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