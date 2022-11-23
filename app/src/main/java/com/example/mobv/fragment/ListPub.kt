package com.example.mobv.fragment

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mobv.R
import com.example.mobv.adapter.PubAdapter
import com.example.mobv.databinding.FragmentListPubBinding
import com.example.mobv.helper.Injection
import com.example.mobv.helper.PreferenceData
import com.example.mobv.model.PubsSingleton
import com.example.mobv.viewmodel.BarsViewModel
import kotlinx.android.synthetic.main.fragment_list_pub.*
import kotlinx.android.synthetic.main.fragment_list_pub.view.*
import java.util.*

class ListPub : Fragment() {

    private var _binding: FragmentListPubBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel: BarsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(BarsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPubBinding.inflate(inflater, container, false)
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

        binding.listPubButtonSort.setOnClickListener{
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
                ListPubDirections.actionRegistrationToRegistration()
            )


        }

        binding.listPubButtonAdd.setOnClickListener{
            findNavController().navigate(
                ListPubDirections.actionRegistrationToNewPub()
            )
        }
//        pass obj to Adapter for Recycler
        binding.ListPubRecyclerView.ListPub_recyclerView.adapter = PubAdapter(requireContext(), pubs.elements!! ,findNavController())

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        binding.ListPubRecyclerView.ListPub_recyclerView.setHasFixedSize(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swiperefresh.setOnRefreshListener {
            Log.d("testingOut: ", "TestingOnRefreshListener")
            Log.d("testingOut: ", "TestingOnRefreshListener2")

            viewmodel.refreshData()

            // on below line we are setting is refreshing to false.
            swiperefresh.isRefreshing = false

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}