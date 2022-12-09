package com.example.mobv.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobv.DataRepository
import kotlinx.coroutines.launch

class FriendViewModel(private val repository: DataRepository): ViewModel() {

    val loading = MutableLiveData(false)
    val friends = MutableLiveData<String>(null)


//    val bars: LiveData<List<Bar>?> =
//        liveData {
//            loading.postValue(true)
//            repository.apiBarList()
//            loading.postValue(false)
////            emitSource(repository.dbBars())
//        }

    fun addFriend(name: String, context: Context){
        viewModelScope.launch {
//            loading.postValue(true)
            repository.apiAddFriend(name, context)
            loading.postValue(false)
        }
    }

    fun getFriendList(context: Context){
        viewModelScope.launch {
//            loading.postValue(true)
            friends.postValue(repository.apiGetFriendList(context))
            loading.postValue(false)
        }
    }
}