package com.rempahpedia.rempahpedia.bottomnav.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.rempahpedia.rempahpedia.R
import com.rempahpedia.rempahpedia.databinding.FragmentHomeBinding
import com.rempahpedia.rempahpedia.listspices.SpicesActivity
import com.rempahpedia.rempahpedia.ui.classification.ResultActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAnalyze = binding.btnAnalyze
        val btnExplore = binding.btnExplore
        val rempahFirst = binding.rempahFirst
        val rempahSecond = binding.rempahSecond

        btnAnalyze.setOnClickListener {
            val intent = Intent(activity, ResultActivity::class.java)
            startActivity(intent)
        }

        btnExplore.setOnClickListener {
            val intent = Intent(activity, SpicesActivity::class.java)
            startActivity(intent)
        }

        val requestOptions = RequestOptions().transform(RoundedCorners(18))

        Glide.with(this)
            .load(R.drawable.rempah)
            .apply(requestOptions)
            .into(rempahFirst)

        Glide.with(this)
            .load(R.drawable.rempah2)
            .apply(requestOptions)
            .into(rempahSecond)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
