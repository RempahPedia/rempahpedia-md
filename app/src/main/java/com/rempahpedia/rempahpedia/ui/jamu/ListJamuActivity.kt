package com.rempahpedia.rempahpedia.ui.jamu

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuResponseItem
import com.rempahpedia.rempahpedia.databinding.ActivityListJamuBinding

class ListJamuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListJamuBinding
    private val jamuViewModel: JamuViewModel by viewModels<JamuViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListJamuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jamuViewModel.listJamu.observe(this) { jamu ->
            setJamuData(jamu)
        }
        jamuViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvJamu.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvJamu.addItemDecoration(itemDecoration)
    }

    private fun setJamuData(jamus: List<JamuResponseItem>) {
        val adapter = JamuAdapter()
        adapter.submitList(jamus)
        binding.rvJamu.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}