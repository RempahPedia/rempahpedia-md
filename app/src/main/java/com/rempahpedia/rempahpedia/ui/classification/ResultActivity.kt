package com.rempahpedia.rempahpedia.ui.classification

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rempahpedia.rempahpedia.R
import com.rempahpedia.rempahpedia.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra(IMAGE_URI))
        binding.resultImage.setImageURI(imageUri)
        binding.resultText.text = getString(R.string.ml_soon)
    }

    companion object {
        const val IMAGE_URI = "imageURI"
    }
}