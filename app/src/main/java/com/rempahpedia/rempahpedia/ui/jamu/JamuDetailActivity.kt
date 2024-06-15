package com.rempahpedia.rempahpedia.ui.jamu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rempahpedia.rempahpedia.databinding.ActivityJamuDetailBinding

class JamuDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJamuDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJamuDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(ID, 0)
        binding.descTextView.text = id.toString()
    }

    companion object {
        const val ID = "idJamu"
    }
}