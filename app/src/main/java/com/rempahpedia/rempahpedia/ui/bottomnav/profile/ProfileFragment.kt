package com.rempahpedia.rempahpedia.ui.bottomnav.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rempahpedia.rempahpedia.databinding.FragmentProfileBinding
import com.rempahpedia.rempahpedia.ui.OnBoardingActivity
import com.rempahpedia.rempahpedia.ui.auth.AuthViewModel
import com.rempahpedia.rempahpedia.ui.auth.AuthViewModelFactory
import com.rempahpedia.rempahpedia.ui.rempah.RempahViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory.getInstance(requireActivity())
    }
    private val rempahViewModel: RempahViewModel by viewModels<RempahViewModel>()

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

                rempahViewModel.getRempahUnlocked("access_token=${user.token}")
                rempahViewModel.rempahUnlocked.observe(viewLifecycleOwner) { rempahUnlocked ->
                    "Rempah: $rempahUnlocked/30".also { binding.rempahUnlocked.text = it }
                }
                rempahViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                    Toast.makeText(binding.root.context, errorMessage, Toast.LENGTH_SHORT).show()
                }
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