package com.asadevelopers.findmovies.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.asadevelopers.findmovies.MainActivity
import com.asadevelopers.findmovies.MySingleton
import com.asadevelopers.findmovies.adapters.ButtonCLicked
import com.asadevelopers.findmovies.adapters.CategoriesAdapter
import com.asadevelopers.findmovies.databinding.FragmentAllCategoryBinding
import com.asadevelopers.findmovies.models.Category
import com.asadevelopers.findmovies.models.Movie

class AllCategoryFragment : Fragment(), ButtonCLicked {

    private lateinit var binding: FragmentAllCategoryBinding
    private val URL = "https://api.themoviedb.org/3/genre/movie/list?api_key=2ca504baeb0a1f37b6398b74a4bff058&language=en-US"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllCategoryBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        getCategories(URL, binding.rvBtnCategories)

        return view
    }

    private fun getCategories(url: String, rvBtnCategories: RecyclerView) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.i("Response", response.toString())
                val categoriesArray = response.getJSONArray("genres")
                val categoriesList = ArrayList<Category>()
                for(i in 0 until categoriesArray.length()){
                    val jsonObject = categoriesArray.getJSONObject(i)
                    val category = Category(jsonObject.getInt("id"),
                                jsonObject.getString("name"))
                    categoriesList.add(category)
                }
                val adapter = CategoriesAdapter(categoriesList, this)
                rvBtnCategories.layoutManager = GridLayoutManager(context, 2)
                rvBtnCategories.adapter = adapter

            },
            { error ->

            })
        MySingleton.getInstance(context!!).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(category: Category) {

        val fragment = CategoryFragment()
        val bundle = Bundle()
        var url = "https://api.themoviedb.org/3/discover/movie?api_key=2ca504baeb0a1f37b6398b74a4bff058&include_adult=false&with_genres="
        url += category.id
        bundle.putString("categoryId", url)
        bundle.putString("Name", category.name)
        fragment.arguments  = bundle
        (activity as MainActivity).setCurrentFragment(fragment)

    }

}