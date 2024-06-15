package com.rempahpedia.rempahpedia.ui.auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rempahpedia.rempahpedia.R
import com.rempahpedia.rempahpedia.data.pref.UserModel
import com.rempahpedia.rempahpedia.data.remote.api.ApiConfig
import com.rempahpedia.rempahpedia.data.remote.auth.AuthRequest
import com.rempahpedia.rempahpedia.databinding.ActivityLoginBinding
import com.rempahpedia.rempahpedia.ui.MainActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {
    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            binding.progressBar.visibility = View.VISIBLE

            lifecycleScope.launch {
                try {
                    val authRequest = AuthRequest(email, password)
                    val response = ApiConfig.getApiService().login(authRequest)
                    val token = response.userCredential.user.stsTokenManager.accessToken

                    binding.progressBar.visibility = View.GONE
                    showAlertDialog(
                        title = getString(R.string.success),
                        message = response.message,
                        success = true
                    )

                    authViewModel.saveSession(UserModel(email, token))
                } catch (e: HttpException) {
                    binding.progressBar.visibility = View.GONE
                    showAlertDialog(
                        title = getString(R.string.failed),
                        message = "gagal login",
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
                setPositiveButton("continue") { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
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