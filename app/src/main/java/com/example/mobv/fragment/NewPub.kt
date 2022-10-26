package com.example.mobv.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mobv.databinding.FragmentNewPubBinding
import com.example.mobv.model.Pub
import com.example.mobv.model.PubsSingleton
import com.example.mobv.model.Tags

/**
 * A simple [Fragment] subclass.
 * Use the [NewPub.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewPub : Fragment() {

    private var _binding: FragmentNewPubBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPubBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.newPubButtonSend.setOnClickListener {
            try {
                val tags =  Tags(
                    binding.newPubPubName.text.toString(),
                    binding.newPubOpeningHours.text.toString(),
                    binding.newPubWebsite.text.toString(),
                    binding.newPubPhoneNumber.text.toString()
                )
                val pub =   Pub(
                    null,
                    binding.newPubLatitude.text.toString().toInt(),
                    binding.newPubLongitude.text.toString().toFloat(),
                    tags
                )
                PubsSingleton.pubs.elements?.add(pub)
                findNavController().navigate(
                    NewPubDirections.actionNewPubToRegistration()
                )
            }
            catch(e: Exception){
                Toast.makeText(activity,"All fields are required!",Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}