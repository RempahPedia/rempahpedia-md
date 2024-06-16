package com.rempahpedia.rempahpedia.ui.rempah

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.rempahpedia.rempahpedia.data.remote.rempah.RempahResponseItem
import com.rempahpedia.rempahpedia.databinding.ActivityRempahListBinding
import com.rempahpedia.rempahpedia.ui.OnBoardingActivity
import com.rempahpedia.rempahpedia.ui.auth.AuthViewModel
import com.rempahpedia.rempahpedia.ui.auth.AuthViewModelFactory

class RempahListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRempahListBinding

    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory.getInstance(this)
    }
    private val rempahViewModel: RempahViewModel by viewModels<RempahViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRempahListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            } else {
                rempahViewModel.getAllRempah("access_token=${user.token}")
                rempahViewModel.listRempah.observe(this) { rempah ->
                    setRempahData(rempah)
                }

                rempahViewModel.isLoading.observe(this) { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                }

                rempahViewModel.errorMessage.observe(this) { errorMessage ->
                    Toast.makeText(binding.root.context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.rvRempah.layoutManager = GridLayoutManager(this, 2)
    }

    private fun setRempahData(rempahs: List<RempahResponseItem>) {
        val adapter = RempahAdapter()
        adapter.submitList(rempahs)
        binding.rvRempah.adapter = adapter
    }
}