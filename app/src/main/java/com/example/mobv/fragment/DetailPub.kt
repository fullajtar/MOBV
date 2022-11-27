package com.example.mobv.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mobv.databinding.FragmentDetailPubBinding
import androidx.navigation.fragment.findNavController
import com.example.mobv.model.PubsSingleton


class DetailPub : Fragment() {

    private var _binding: FragmentDetailPubBinding? = null
    private val binding get() = _binding!!

    // get the arguments from the PubList fragment
    private val args : DetailPubArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPubBinding.inflate(inflater, container, false)
        val view = binding.root

        // Receive the arguments in a variable
        val pub = args.pub

        // set the values to respective textViews
        binding.detailPubPubNameValue.text = pub.tags!!.name.toString()
        binding.detailPubWebsiteValue.text = pub.tags!!.website.toString()
        binding.detailPubPhoneNumberValue.text = pub.tags!!.phone.toString()
        binding.detailPubOpeningHoursValue.text = pub.tags!!.opening_hours.toString()

        val latitudeString = pub.lat.toString()
        val longitudeString = pub.lon.toString()

        binding.detailPubLatitudeValue.text = latitudeString
        binding.detailPubLongitudeValue.text = longitudeString

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
                DetailPubDirections.actionDetailToListPub()
            )
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}