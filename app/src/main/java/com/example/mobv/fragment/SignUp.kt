package com.example.mobv.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobv.R
import com.example.mobv.api.BodyGetAllPubs
import com.example.mobv.databinding.FragmentSignInBinding
import com.example.mobv.databinding.FragmentSignUpBinding
import com.example.mobv.viewmodel.AuthViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUp.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUp : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = AuthViewModel()
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

        binding.buttonSignIn.setOnClickListener{
            findNavController().navigate(
                SignUpDirections.actionSignUpToSignIn()
            )
        }

        binding.buttonSignUp.setOnClickListener{
            if (binding.signupUsername.text.toString().isNotBlank() && binding.signupPassword.text.toString().isNotBlank()
                && binding.signupPassword.text.toString().compareTo(binding.signupPassword2.text.toString())==0) {
                authViewModel.signup(
                    binding.signupUsername.text.toString(),
                    binding.signupPassword.text.toString(),
                    requireContext()
                )
            } else if (binding.signupUsername.text.toString().isBlank() || binding.signupPassword.text.toString().isBlank()){
                Toast.makeText(activity,"Fill in name and password", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity,"Passwords must be matching", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUp.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUp().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}