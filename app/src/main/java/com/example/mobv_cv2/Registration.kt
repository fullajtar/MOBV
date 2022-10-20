package com.example.mobv_cv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.navigation.fragment.findNavController
import com.example.mobv_cv2.databinding.FragmentRegistrationBinding

class Registration : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val view = binding.root

        // call onClick on the SendButton
        binding.buttonSend.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etRestaurantName.text.toString()
            val password = binding.etLatitude.text.toString().toFloat()
            val longitude = binding.etLongitude.text.toString().toFloat()

            // create user object and pass the
            // required arguments
            // that is name, email,and password
            val form = Form(name,email, password, longitude)

            // create an action and pass the required user object to it
            // If you can not find the RegistrationDirection try to "Build project"
            val action = RegistrationDirections.actionRegistrationToDetails(form)

            // this will navigate the current fragment i.e
            // Registration to the Detail fragment
            findNavController().navigate(
                action
            )
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}