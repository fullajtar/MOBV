package com.example.mobv.viewmodel
//source: https://github.com/marosc/mobv2022

import android.util.Log
import androidx.lifecycle.*
import com.example.mobv.DataRepository
import com.example.mobv.model.Bar
import com.example.mobv.model.BarsSingleton
import com.example.mobv.model.BarsSingleton.bars
import com.example.mobv.model.PubsSingleton
import kotlinx.coroutines.launch

class BarsViewModel(private val repository: DataRepository): ViewModel() {

    val loading = MutableLiveData(false)
    val bars = MutableLiveData<String>(null)


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