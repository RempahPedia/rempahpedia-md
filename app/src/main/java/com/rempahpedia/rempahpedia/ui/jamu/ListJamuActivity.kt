package com.rempahpedia.rempahpedia.ui.jamu

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

        jamuViewModel.getAllJamu()
        jamuViewModel.listJamu.observe(this) { jamu ->
            setJamuData(jamu)
        }

        jamuViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        jamuViewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(binding.root.context, errorMessage, Toast.LENGTH_SHORT).show()
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvJamu.layoutManager = layoutManager

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val keyword = textView.text.toString()
                        jamuViewModel.searchJamu(keyword, "")
                        searchBar.setText(keyword)
                        searchView.hide()
                        true
                    } else {
                        false
                    }
                }
        }
    }

    private fun setJamuData(jamus: List<JamuResponseItem>) {
        val adapter = JamuAdapter()
        adapter.submitList(jamus)
        binding.rvJamu.adapter = adapter
    }
}