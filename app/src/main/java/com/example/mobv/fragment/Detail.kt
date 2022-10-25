package com.example.mobv.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.mobv.databinding.FragmentDetailBinding
import androidx.navigation.fragment.findNavController
import com.example.mobv.R
import com.example.mobv.model.PubsSingleton
import com.example.mobv.model.PubsSingleton.pubs


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
        val pub = args.pub

        // set the values to respective textViews
        binding.tvRestaurantName.text = pub.tags!!.name.toString()
        binding.detailWebpageValue.text = pub.tags!!.website.toString()
        binding.detailPhoneValue.text = pub.tags!!.phone.toString()
        binding.detailOpenhoursValue.text = pub.tags!!.opening_hours.toString()

        val latitudeString = pub.lat.toString()
        val longitudeString = pub.lon.toString()

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

        binding.detailRemoveButton.setOnClickListener {
            PubsSingleton.pubs.elements!!.remove(pub)
            findNavController().navigate(
                DetailDirections.actionDetailToRegistration()
            )

        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}