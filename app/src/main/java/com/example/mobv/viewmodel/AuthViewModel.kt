package com.example.mobv.viewmodel
//source: https://github.com/marosc/mobv2022

import androidx.lifecycle.*
import com.example.mobv.helper.Evento

class AuthViewModel(/**private val repository: DataRepository**/): ViewModel() {
    private val _message = MutableLiveData<Evento<String>>()
    val message: LiveData<Evento<String>>
        get() = _message

//    val user= MutableLiveData<UserResponse>(null)

    val loading = MutableLiveData(false)

//    fun login(name: String, password: String){
//        viewModelScope.launch {
//            loading.postValue(true)
//            repository.apiUserLogin(
//                name,password,
//                { _message.postValue(Evento(it)) },
//                { user.postValue(it) }
//            )
//            loading.postValue(false)
//        }
//    }
//
//    fun signup(name: String, password: String){
//        viewModelScope.launch {
//            loading.postValue(true)
//            repository.apiUserCreate(
//                name,password,
//                { _message.postValue(Evento(it)) },
//                { user.postValue(it) }
//            )
//            loading.postValue(false)
//        }
//    }

    fun show(msg: String){ _message.postValue(Evento(msg))}
}