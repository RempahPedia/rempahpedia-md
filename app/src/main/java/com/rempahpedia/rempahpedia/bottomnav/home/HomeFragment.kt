package com.rempahpedia.rempahpedia.bottomnav.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rempahpedia.rempahpedia.R
import com.rempahpedia.rempahpedia.databinding.FragmentHomeBinding
import com.rempahpedia.rempahpedia.listspices.SpicesActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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


        }

        btnExplore.setOnClickListener {
            val intent = Intent(activity, SpicesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
