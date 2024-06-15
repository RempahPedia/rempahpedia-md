package com.rempahpedia.rempahpedia.ui.jamu

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuResponseItem
import com.rempahpedia.rempahpedia.databinding.ActivityListJamuBinding

class ListJamuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListJamuBinding
    private lateinit var filterRecyclerView: RecyclerView
    private lateinit var filterAdapter: FilterAdapter

    private val jamuViewModel: JamuViewModel by viewModels<JamuViewModel>()

    private var typedKeyword: String = ""
    private var selectedFilter: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListJamuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filterRecyclerView = binding.rvFilter
        filterRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

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

        val filters = listOf(
            "Semua",
            "Nyeri Sendi",
            "Hipertensi",
            "Batuk",
            "Insomnia",
            "Anemia",
            "Imunitas",
            "Radang Tenggorokan",
            "Haid",
            "Gangguan Pencernaan",
            "Kolesterol",
            "Jantung"
        )
        filterAdapter = FilterAdapter(filters) { selectedPosition ->
            selectedPosition?.let { index ->
                val filter = filters[index]
                selectedFilter = if (filter == "Semua") {
                    ""
                } else {
                    filter
                }
                jamuViewModel.searchJamu(typedKeyword, selectedFilter)
            }
        }
        filterRecyclerView.adapter = filterAdapter

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        typedKeyword = textView.text.toString()
                        jamuViewModel.searchJamu(typedKeyword, selectedFilter)
                        searchBar.setText(typedKeyword)
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