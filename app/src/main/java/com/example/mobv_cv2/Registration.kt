package com.example.mobv_cv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

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
            val restaurantName = binding.etRestaurantName.text.toString()

            if (binding.etLatitude.text.toString().isEmpty() or
                binding.etLongitude.text.toString().isEmpty() or
                binding.etRestaurantName.text.toString().isEmpty() or
                binding.etName.text.toString().isEmpty()
//                binding.etLatitude.text.toString().equals('.') or
//                binding.etLongitude.text.toString().equals('.')
                ) {

                Toast.makeText(activity,"All fields are required!",Toast.LENGTH_SHORT).show()
                findNavController().navigate(
                    RegistrationDirections.actionRegistrationToRegistration()
                )
            }
            else{
                val latitude = binding.etLatitude.text.toString().toFloat()
                val longitude = binding.etLongitude.text.toString().toFloat()

                // create an action and pass the required user object to it
                // If you can not find the RegistrationDirection try to "Build project"
                val form = Form(name,restaurantName, latitude, longitude)

                findNavController().navigate(
                    // create an action and pass the required user object to it
                    // If you can not find the RegistrationDirection try to "Build project"
                    // this will navigate the current fragment i.e
                    // Registration to the Detail fragment
                    RegistrationDirections.actionRegistrationToDetails(form)
                )
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}