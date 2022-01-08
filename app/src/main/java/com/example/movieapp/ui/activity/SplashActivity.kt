package com.example.movieapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movieapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.animationView.alpha = 0F
        binding.animationView.animate().setDuration(10000).alpha(1F).withEndAction {
            val startMainActivityIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(startMainActivityIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}