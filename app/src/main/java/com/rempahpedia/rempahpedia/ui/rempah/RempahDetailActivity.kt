package com.rempahpedia.rempahpedia.ui.rempah

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rempahpedia.rempahpedia.databinding.ActivityRempahDetailBinding
import java.lang.StringBuilder

class RempahDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRempahDetailBinding
    private val rempahViewModel: RempahViewModel by viewModels<RempahViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRempahDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(ID, 0)
        rempahViewModel.getRempahById(id)
        rempahViewModel.rempahDetail.observe(this) { rempahDetail ->
            val rempahName = rempahDetail.nama
            val deskripsi = rempahDetail.deskripsi
            val listManfaat = rempahDetail.manfaat
            val imageUrl = rempahDetail.imageUrl

            val manfaat = StringBuilder()
            manfaat.append("$rempahName diketahui memiliki berbagai khasiat, seperti:\n")
            for ((i, e) in listManfaat.withIndex()) {
                manfaat.append("${i + 1}. $e\n")
            }

            Glide.with(this)
                .load(imageUrl)
                .into(binding.rempahImg)
            binding.apply {
                tvRempahName.text = rempahName
                tvDeskripsi.text = deskripsi
                tvManfaat.text = manfaat
            }
        }
    }

    companion object {
        const val ID = "idRempah"
    }
}