package com.example.mobv.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mobv.R
import com.example.mobv.api.PubsApi
import com.example.mobv.databinding.FragmentNewPubBinding
import com.example.mobv.databinding.FragmentSignInBinding
import com.example.mobv.databinding.FragmentSignUpBinding
import com.example.mobv.helper.Injection
import com.example.mobv.helper.PreferenceData
import com.example.mobv.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class SignIn : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val x = PreferenceData.getInstance().getUserItem(requireContext())
        if ((x?.uid != null)) {
            Log.d("GameFragment", "You re logged in already")

            findNavController().navigate(
                SignInDirections.actionSignInToListPub()
            )
            return
        }
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = authViewModel
        }

        binding.buttonSignUp.setOnClickListener{
            findNavController().navigate(
                SignInDirections.actionSignInToSignUp()
            )
        }

        binding.buttonSignIn.setOnClickListener{
            if (binding.signinUsername.text.toString().isNotBlank() && binding.signinPassword.text.toString().isNotBlank()) {
                authViewModel.signin(
                    binding.signinUsername.text.toString(),
                    binding.signinPassword.text.toString(),
                    requireContext()
                )
            } else if (binding.signinUsername.text.toString().isBlank() || binding.signinPassword.text.toString().isBlank()){
                Toast.makeText(activity,"Fill in name and password", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity,"Passwords must be matching", Toast.LENGTH_SHORT).show()
            }
        }

        authViewModel.user.observe(viewLifecycleOwner){
            it?.let {
                PreferenceData.getInstance().putUserItem(requireContext(),it)
                Navigation.findNavController(requireView()).navigate(R.id.action_signIn_to_listPub)
            }
        }
    }
}