package com.example.project

import com.example.project.databinding.ActivityMainBinding


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

/*
MainActivity acts as the nav host fragment for this project
Handles the bottom navigation bar's navigation
*/
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController)
        binding.bottomNavigationView.menu.findItem(R.id.homeId).isChecked = true
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeId -> navController.navigate(R.id.homePage)
                R.id.favouritesId -> navController.navigate(R.id.myFavourites)
                R.id.listId -> navController.navigate(R.id.pmListFragment)

                else -> {

                }
            }
            true
        }
    }

    /*
    add the back button to go back to the last fragment
    this needs the setupActionBarWithNavController(navController) to work
    */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}