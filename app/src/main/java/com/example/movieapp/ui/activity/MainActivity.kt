package com.example.movieapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.ui.fragment.FavoriteFragment
import com.example.movieapp.ui.fragment.HomeFragment
import com.example.movieapp.ui.fragment.SearchFragment
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var fragmentTransaction : FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> replaceFragment(HomeFragment())
                R.id.searchFragment -> replaceFragment(SearchFragment())
                else -> replaceFragment(FavoriteFragment())
                }
        }
    }

    private fun replaceFragment(fragment : Fragment): Boolean {
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        return true
    }
}


