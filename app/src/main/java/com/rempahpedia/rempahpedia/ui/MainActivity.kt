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
import com.rempahpedia.rempahpedia.bottomnav.camera.CameraFragment
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

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_LONG)
                    .show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        authViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            }
        }

//        binding.logoutButton.setOnClickListener {
//            authViewModel.logout()
//        }
//
//        binding.captureImage.setOnClickListener {
//            startCameraX()
//        }

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
                R.id.camera -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, CameraFragment())
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

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            val currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)

            val resultIntent = Intent(this@MainActivity, ResultActivity::class.java)
            resultIntent.putExtra(ResultActivity.IMAGE_URI, currentImageUri)
            startActivity(resultIntent)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val CAMERAX_RESULT = 123 // replace with your actual result code
    }
}