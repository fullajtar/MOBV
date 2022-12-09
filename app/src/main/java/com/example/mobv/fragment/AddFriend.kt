package com.example.mobv.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobv.databinding.FragmentAddFriendBinding
import com.example.mobv.helper.Injection
import com.example.mobv.viewmodel.FriendViewModel

class AddFriend : Fragment() {
    private var _binding: FragmentAddFriendBinding? = null
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
        _binding = FragmentAddFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addFriendButtonAddFriend.setOnClickListener {
            viewmodel.addFriend(binding.addFriendTextFriendsName.text.toString(), requireContext())
            findNavController().navigate(
                AddFriendDirections.actionAddFriendToFriendList()
            )
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}