package com.example.mobv.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mobv.R
import com.example.mobv.api.BodyGetAllPubs
import com.example.mobv.databinding.FragmentSignInBinding
import com.example.mobv.databinding.FragmentSignUpBinding
import com.example.mobv.helper.HashPassword
import com.example.mobv.helper.Injection
import com.example.mobv.helper.PreferenceData
import com.example.mobv.viewmodel.AuthViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUp : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
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
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val x = PreferenceData.getInstance().getUserItem(requireContext())
        if ((x?.uid != null)) {
            Log.d("GameFragment", "You re logged in already")

            findNavController().navigate(
                SignUpDirections.actionSignUpToListPub()
            )

        }
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = authViewModel
        }

        binding.buttonSignIn.setOnClickListener{
            findNavController().navigate(
                SignUpDirections.actionSignUpToSignIn()
            )
        }

        binding.buttonSignUp.setOnClickListener{
            Log.d("GameFragment", "You re logged in already")
            val hashedpassword = HashPassword.hashPassword(binding.signupPassword.text.toString())

            if (binding.signupUsername.text.toString().isNotBlank() && binding.signupPassword.text.toString().isNotBlank()
                && binding.signupPassword.text.toString().compareTo(binding.signupPassword2.text.toString())==0
                && hashedpassword != null
            ) {
                Log.d("GameFragment", "First IF")
                authViewModel.signup(
                    binding.signupUsername.text.toString(),
                    hashedpassword,
                    requireContext()
                )
            } else if (binding.signupUsername.text.toString().isBlank() || binding.signupPassword.text.toString().isBlank()){
                Toast.makeText(activity,"Fill in name and password", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity,"Passwords must be matching", Toast.LENGTH_SHORT).show()
            }
        }
        authViewModel.user.observe(viewLifecycleOwner){
            it?.let {
                PreferenceData.getInstance().putUserItem(requireContext(),it)
                Navigation.findNavController(requireView()).navigate(R.id.action_signUp_to_listPub)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}