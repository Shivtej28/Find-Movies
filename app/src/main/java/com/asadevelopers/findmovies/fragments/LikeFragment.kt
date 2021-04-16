package com.asadevelopers.findmovies.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.asadevelopers.findmovies.MainActivity
import com.asadevelopers.findmovies.adapters.LikedMoviesAdapter
import com.asadevelopers.findmovies.adapters.OnDeleteItem
import com.asadevelopers.findmovies.adapters.onClickedItem
import com.asadevelopers.findmovies.databinding.FragmentLikeBinding
import com.asadevelopers.findmovies.db.LikedMovieViewModel
import com.asadevelopers.findmovies.db.MovieDetail
import com.asadevelopers.findmovies.models.Movie

class LikeFragment : Fragment(), OnDeleteItem, onClickedItem {

    private lateinit var binding: FragmentLikeBinding
    lateinit var activity1: MainActivity
    lateinit var model: LikedMovieViewModel
    lateinit var adapter : LikedMoviesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLikeBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        activity1 = activity as MainActivity
        // viewModel = ViewModelProvider(activity, activity.factory).get(LikedMovieViewModel::class.java)
        model = activity?.run {
            ViewModelProviders.of(this)[LikedMovieViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        binding.rvLikedMovies.layoutManager = LinearLayoutManager(activity)
        adapter = LikedMoviesAdapter(listOf(), this, this)
        binding.rvLikedMovies.adapter = adapter


        model.getAllMovies().observe(activity1, Observer {
            adapter.list = it
            Log.i("List",it.toString() )
            adapter.notifyDataSetChanged()
        })
        return view
    }

    override fun onItemClicked(movieDetail: MovieDetail) {

        model.delete(movieDetail)
        model.getAllMovies().observe(activity1, Observer {
            adapter.list = it
            Log.i("List",it.toString() )
            adapter.notifyDataSetChanged()
        })
    }

    override fun onClicked(movieDetail: MovieDetail) {
        val movie = movieDetail
        val fragment = MovieDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)
        fragment.arguments = bundle
        (activity as MainActivity).setCurrentFragment(fragment)
    }


}