package com.example.mobv.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobv.adapter.PubAdapter
import com.example.mobv.databinding.FragmentListPubBinding
import com.example.mobv.helper.Injection
import com.example.mobv.helper.PreferenceData
import com.example.mobv.model.BarsSingleton
import com.example.mobv.viewmodel.BarsViewModel
import kotlinx.android.synthetic.main.fragment_list_pub.*
import kotlinx.android.synthetic.main.fragment_list_pub.view.*

//TODO AFTER LOGIN TIMEOUT REDIRECT TO LOGIN
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
        viewmodel.refreshData()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listPubButtonSort.setOnClickListener{
            BarsSingleton.toggleSortByName()
            findNavController().navigate(
                ListPubDirections.actionListPubToListPub()
            )
        }

        binding.listPubButtonSortUsers.setOnClickListener{
            BarsSingleton.toggleSortByUsers()
            findNavController().navigate(
                ListPubDirections.actionListPubToListPub()
            )
        }

        binding.listPubButtonSort.setOnClickListener{
            BarsSingleton.toggleSortByUsers()
            findNavController().navigate(
                ListPubDirections.actionListPubToListPub()
            )
        }

        binding.listPubButtonAdd.setOnClickListener{
            findNavController().navigate(
                ListPubDirections.actionListPubToNewPub()
            )
        }

        binding.listPubButtonSignOut.setOnClickListener{
            PreferenceData.getInstance().clearData(requireContext())
            findNavController().navigate(
                ListPubDirections.actionListPubToSignIn()
            )
        }

        binding.listPubButtonFriendsList.setOnClickListener{
            findNavController().navigate(
                ListPubDirections.actionListPubToFriendList()
            )
        }

        binding.listPubButtonCheckIntoBar.setOnClickListener{
            findNavController().navigate(
                ListPubDirections.actionListPubToCheckIntoBar()
            )
        }

        swiperefresh.setOnRefreshListener {
            viewmodel.refreshData()
            swiperefresh.isRefreshing = false
        }

        viewmodel.bars.observe(viewLifecycleOwner) {
            if (it != null && it.equals("Success")) {
                BarsSingleton.sortBy()

                if (binding.ListPubRecyclerView.adapter != null){
                    val adapter = binding.ListPubRecyclerView.adapter!! as PubAdapter
                    adapter.update(BarsSingleton.bars!!)
                } else{
                    binding.ListPubRecyclerView.adapter = PubAdapter(requireContext(), BarsSingleton.bars!!,findNavController())
                    binding.ListPubRecyclerView.setHasFixedSize(true)
                }
            }
            else if (it != null && it.equals("Failure")){
                Toast.makeText(activity,"Error loading data!", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}