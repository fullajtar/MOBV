package com.example.mobv

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mobv.databinding.FragmentDetailBinding
import com.example.mobv_cv2.databinding.FragmentDetailBinding


class Detail : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    // get the arguments from the Registration fragment
    private val args : DetailArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        // Receive the arguments in a variable
        val formDetails = args.form

        // set the values to respective textViews
        binding.tvName.text = formDetails.name
        binding.tvRestaurantName.text = formDetails.restaurantName
        val latitudeString = formDetails.latitude.toString()
        val longitudeString = formDetails.longitude.toString()

        binding.tvLatitude.text = latitudeString
        binding.tvLongitude.text = longitudeString

        binding.buttonShowMap.setOnClickListener{
            val packageManager = requireActivity().packageManager
            val gmmIntentUri = Uri.parse("geo:$latitudeString,$longitudeString")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(packageManager)?.let {
                startActivity(mapIntent)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}