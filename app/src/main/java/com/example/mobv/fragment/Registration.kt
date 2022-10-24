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