package com.example.mobv.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mobv.R
import com.example.mobv.api.PubsApi
import com.example.mobv.databinding.FragmentNewPubBinding
import com.example.mobv.databinding.FragmentSignInBinding
import com.example.mobv.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignIn.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignIn : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentSignInBinding? = null
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
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                val _api: PubsApi = PubsApi.create(requireContext())

                lifecycleScope.launch {
                    val result = _api.barList()
                    Log.d("testingOut: ", result.toString())


                }



                //TODO: CHECK IF LOGIN WAS SUCCESSFUL!
                findNavController().navigate(
                    SignInDirections.actionSignInToRegistration()
                )
            } else if (binding.signinUsername.text.toString().isBlank() || binding.signinPassword.text.toString().isBlank()){
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
         * @return A new instance of fragment SignIn.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignIn().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}