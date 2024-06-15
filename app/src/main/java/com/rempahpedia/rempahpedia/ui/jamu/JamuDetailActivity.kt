package com.rempahpedia.rempahpedia.ui.jamu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rempahpedia.rempahpedia.R
import com.rempahpedia.rempahpedia.databinding.ActivityJamuDetailBinding

class JamuDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJamuDetailBinding
    private val jamuViewModel: JamuViewModel by viewModels<JamuViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJamuDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvKhasiat.text = getString(R.string.berbagai_khasiat)
        binding.tvMembuat.text = getString(R.string.berbagai_rempah)
        binding.tvMenyembuhkan.text = getString(R.string.berbagai_penyakit)

        val id = intent.getIntExtra(ID, 0)
        jamuViewModel.getJamuById(id)
        jamuViewModel.jamuDetail.observe(this) { jamuDetail ->
            val jamuName = jamuDetail.nama
            val listManfaat = jamuDetail.manfaat
            val listRempah = jamuDetail.rempah
            val listPenyakit = jamuDetail.penyakit

            val manfaat = StringBuilder()
            val rempah = StringBuilder()
            val penyakit = StringBuilder()

            for ((i, e) in listManfaat.withIndex()) {
                manfaat.append("${i + 1}. $e\n")
            }

            for ((i, e) in listRempah.withIndex()) {
                rempah.append("${i + 1}. $e\n")
            }

            for ((i, e) in listPenyakit.withIndex()) {
                penyakit.append("${i + 1}. $e\n")
            }

            binding.tvJamuName.text = jamuName
            binding.tvManfaat.text = manfaat
            binding.tvRempah.text = rempah
            binding.tvPenyakit.text = penyakit
        }

        jamuViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        jamuViewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(binding.root.context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val ID = "idJamu"
    }
}