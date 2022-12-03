package com.example.mobv.helper
//source: https://github.com/marosc/mobv2022

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobv.DataRepository
import com.example.mobv.viewmodel.AuthViewModel
import com.example.mobv.viewmodel.BarsViewModel
import com.example.mobv.viewmodel.CheckIntoBarViewModel
import com.example.mobv.viewmodel.FriendViewModel

class ViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(BarsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BarsViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(FriendViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FriendViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(CheckIntoBarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CheckIntoBarViewModel(repository) as T
        }
//
//        if (modelClass.isAssignableFrom(LocateViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return LocateViewModel(repository) as T
//        }
//
//        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return DetailViewModel(repository) as T
//        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}