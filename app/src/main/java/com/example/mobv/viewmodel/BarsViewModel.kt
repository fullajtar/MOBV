package com.example.mobv.viewmodel
//source: https://github.com/marosc/mobv2022

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobv.DataRepository
import com.example.mobv.model.Coords
import kotlinx.coroutines.launch

class BarsViewModel(private val repository: DataRepository): ViewModel() {

    val loading = MutableLiveData(false)
    val bars = MutableLiveData<String>(null)
    val coords = MutableLiveData<Coords>(null)



//    val bars: LiveData<List<Bar>?> =
//        liveData {
//            loading.postValue(true)
//            repository.apiBarList()
//            loading.postValue(false)
////            emitSource(repository.dbBars())
//        }

    fun refreshData(){
        viewModelScope.launch {
//            loading.postValue(true)
            bars.postValue(repository.apiBarList())
            loading.postValue(false)
        }
    }
}