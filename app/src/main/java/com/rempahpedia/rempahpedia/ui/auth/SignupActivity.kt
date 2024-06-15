package com.rempahpedia.rempahpedia.ui.auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rempahpedia.rempahpedia.R
import com.rempahpedia.rempahpedia.data.remote.api.ApiConfig
import com.rempahpedia.rempahpedia.data.remote.auth.AuthRequest
import com.rempahpedia.rempahpedia.databinding.ActivitySignupBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            binding.progressBar.visibility = View.VISIBLE

            lifecycleScope.launch {
                try {
                    val authRequest = AuthRequest(email, password)
                    val response = ApiConfig.getApiService().register(authRequest)
                    val successMsg = response.message

                    binding.progressBar.visibility = View.GONE
                    showAlertDialog(
                        title = getString(R.string.success),
                        message = successMsg,
                        success = true
                    )
                } catch (e: HttpException) {
                    binding.progressBar.visibility = View.GONE
                    showAlertDialog(
                        title = getString(R.string.failed),
                        message = "gagal registrasi" ,
                        success = false
                    )
                }
            }
        }
    }

    private fun showAlertDialog(title: String, message: String, success: Boolean) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.apply {
            setTitle(title)
            setMessage(message)
            if (success) {
                setPositiveButton("Login") { _, _ ->
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            } else {
                setNegativeButton("Ok") { _, _ ->
                    // Handle negative button click if necessary
                }
            }
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}