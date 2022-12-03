package com.example.mobv.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobv.DataRepository
import com.example.mobv.api.BodyCheckInBar
import com.example.mobv.model.Coords
import com.example.mobv.model.Pubs
import kotlinx.coroutines.launch

class CheckIntoBarViewModel(private val repository: DataRepository): ViewModel() {
    val bars = MutableLiveData<Pubs>(null)
    val coords = MutableLiveData<Coords>(null)
    val selected = MutableLiveData<BodyCheckInBar>(null)


    fun refreshData(lat: Double, lon: Double){
        viewModelScope.launch {
            bars.postValue(repository.apiNearbyBars(lat, lon))
        }
    }

    fun checkInBar(context: Context){
        viewModelScope.launch {
            selected.value?.let { repository.apiBarCheckin(it, context) }
        }
    }
}