package com.rempahpedia.rempahpedia.ui.bottomnav.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rempahpedia.rempahpedia.databinding.FragmentProfileBinding
import com.rempahpedia.rempahpedia.ui.OnBoardingActivity
import com.rempahpedia.rempahpedia.ui.auth.AuthViewModel
import com.rempahpedia.rempahpedia.ui.auth.AuthViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                val intent = Intent(activity, OnBoardingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                val email = user.email
                binding.emailUser.text = email
                binding.userName.text = email.split("@")[0]
            }
        }

        binding.logoutButton.setOnClickListener {
            authViewModel.logout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}