package com.rempahpedia.rempahpedia.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rempahpedia.rempahpedia.R
import com.rempahpedia.rempahpedia.bottomnav.home.HomeFragment
import com.rempahpedia.rempahpedia.bottomnav.profile.ProfileFragment
import com.rempahpedia.rempahpedia.databinding.ActivityMainBinding
import com.rempahpedia.rempahpedia.ui.auth.AuthViewModel
import com.rempahpedia.rempahpedia.ui.auth.AuthViewModelFactory
import com.rempahpedia.rempahpedia.ui.classification.CameraActivity
import com.rempahpedia.rempahpedia.ui.classification.CameraActivity.Companion.CAMERAX_RESULT
import com.rempahpedia.rempahpedia.ui.classification.ResultActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, HomeFragment())
            .commit()

        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, HomeFragment())
                        .commit()
                    true
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, ProfileFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}