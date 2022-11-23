package com.example.mobv.viewmodel
//source: https://github.com/marosc/mobv2022

import androidx.lifecycle.*
import com.example.mobv.DataRepository
import kotlinx.coroutines.launch

class BarsViewModel(private val repository: DataRepository): ViewModel() {

    val loading = MutableLiveData(false)


    fun refreshData(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiBarList()
            loading.postValue(false)
        }
    }
}