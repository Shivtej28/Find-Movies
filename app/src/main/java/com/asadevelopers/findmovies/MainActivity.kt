package com.asadevelopers.findmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.asadevelopers.findmovies.databinding.ActivityMainBinding
import com.asadevelopers.findmovies.db.LikedMovieRepository
import com.asadevelopers.findmovies.db.LikedMovieViewModel
import com.asadevelopers.findmovies.db.MovieDatabase
import com.asadevelopers.findmovies.db.ViewModelFactory
import com.asadevelopers.findmovies.fragments.HomeFragment
import com.asadevelopers.findmovies.fragments.LikeFragment
import com.asadevelopers.findmovies.fragments.SearchFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var  factory: ViewModelProvider.Factory
    private lateinit var binding: ActivityMainBinding
    private  var status : Int = 0
    lateinit var viewModel: LikedMovieViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = MovieDatabase(this)
        val repository = LikedMovieRepository(database)
       factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(LikedMovieViewModel::class.java)




        setCurrentFragment(HomeFragment())

        binding.bottomNav.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.menuHome -> setCurrentFragment(HomeFragment())
                R.id.menuSearch -> setCurrentFragment(SearchFragment())
                R.id.menuLike -> setCurrentFragment(LikeFragment())

            }
            true
        }


    }

     fun setCurrentFragment(fragment: Fragment)  {
         status = 1
         supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()

     }

    override fun onBackPressed() {
        if(status == 1){
            status = 0
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, HomeFragment()).commit()
        }else{
            super.onBackPressed()
        }
    }




}
