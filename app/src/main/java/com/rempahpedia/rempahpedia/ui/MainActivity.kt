package com.rempahpedia.rempahpedia.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rempahpedia.rempahpedia.databinding.ActivityMainBinding
import com.rempahpedia.rempahpedia.ui.auth.AuthViewModel
import com.rempahpedia.rempahpedia.ui.auth.AuthViewModelFactory
import com.rempahpedia.rempahpedia.ui.classification.ResultActivity

class MainActivity : AppCompatActivity() {
    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if the user has already logged in
        authViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            }
        }

        binding.logoutButton.setOnClickListener {
            authViewModel.logout()
        }

        binding.captureImage.setOnClickListener {
            val resultIntent = Intent(this@MainActivity, ResultActivity::class.java)
            startActivity(resultIntent)
        }
    }
}