package com.example.mobv.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobv.adapter.PubAdapter
import com.example.mobv.databinding.FragmentRegistrationBinding
import com.example.mobv.model.PubsSingleton
import kotlinx.android.synthetic.main.fragment_registration.view.*

class Registration : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val view = binding.root
        val pubs = PubsSingleton.pubs
        if (pubs.sortBy != null){
            if (pubs.sortBy!! == "ascending") {
                pubs.elements!!.sortBy { it.tags!!.name }
            }
            else if (pubs.sortBy!! == "descending") {
                pubs.elements!!.sortByDescending { it.tags!!.name }
            }
            else {
                pubs.elements!!.sortBy { it.tags!!.name }
            }
        }

        binding.registrationSortButton.setOnClickListener{
            if (pubs.sortBy == null){
                pubs.sortBy = "ascending"
            }
            else if (pubs.sortBy.equals("ascending")){
                pubs.sortBy = "descending"
            }
            else if (pubs.sortBy.equals("descending")){
                pubs.sortBy = "ascending"
            }

            findNavController().navigate(
                RegistrationDirections.actionRegistrationToRegistration()
            )


        }

        binding.buttonRegistrationAdd.setOnClickListener{
            findNavController().navigate(
                RegistrationDirections.actionRegistrationToNewPub()
            )
        }
//        pass obj to Adapter for Recycler
        binding.recyclerView.recycler_view.adapter = PubAdapter(requireContext(), pubs.elements!! ,findNavController())

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        binding.recyclerView.recycler_view.setHasFixedSize(true)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}