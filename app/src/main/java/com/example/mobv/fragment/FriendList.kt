package com.example.mobv.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobv.adapter.FriendAdapter

import com.example.mobv.databinding.FragmentFriendListBinding
import com.example.mobv.helper.Injection
import com.example.mobv.model.FriendsSingleton

import com.example.mobv.viewmodel.FriendViewModel
import kotlinx.android.synthetic.main.fragment_list_pub.view.*


class FriendList : Fragment() {
    private var _binding: FragmentFriendListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel: FriendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(FriendViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendListBinding.inflate(inflater, container, false)
        val view = binding.root

        if (FriendsSingleton.friends != null){
            val tmp = FriendAdapter(requireContext(), FriendsSingleton.friends!!, findNavController())
            binding.FriendListRecyclerView.adapter = tmp

            // Use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            binding.FriendListRecyclerView.setHasFixedSize(true)
        }
        else{
            viewmodel.getFriendList(requireContext())
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.friendListButtonAdd.setOnClickListener {
            findNavController().navigate(
                FriendListDirections.actionFriendListToAddFriend()
            )
        }

        viewmodel.friends.observe(viewLifecycleOwner){
            if (it != null && it.equals("Success"))
                findNavController().navigate(
                    FriendListDirections.actionFriendListSelf()
                )
            else if (it != null && it.equals("Failure")){
                Toast.makeText(activity,"Error loading data!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}