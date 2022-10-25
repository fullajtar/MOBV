package com.example.mobv.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobv.adapter.PubAdapter
import com.example.mobv.databinding.FragmentRegistrationBinding
import com.example.mobv.model.Form
import com.example.mobv.model.Pubs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

        //read raw json file as string
        val jsonString = requireContext().applicationContext.resources.openRawResource(
            requireContext().applicationContext.resources.getIdentifier(
                "pubs",
                "raw",
                requireContext().applicationContext.packageName
            )
        ).bufferedReader().use { it.readText() }

        //convert json string to obj
        val gson = Gson()
        val sType = object : TypeToken<Pubs>() { }.type
        val otherList = gson.fromJson<Pubs>(jsonString, sType)
//        binding.textView.text = otherList.toString()

//        pass obj to Adapter for Recycler
        binding.recyclerView.recycler_view.adapter = PubAdapter(requireContext(), otherList.elements!! ,findNavController())

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