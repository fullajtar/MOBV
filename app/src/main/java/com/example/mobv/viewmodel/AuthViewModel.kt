package com.example.mobv.viewmodel
//source: https://github.com/marosc/mobv2022

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.mobv.DataRepository
import com.example.mobv.api.BodySignUp
import com.example.mobv.api.PubsApi
import com.example.mobv.api.UserResponse
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: DataRepository): ViewModel() {
    private val _api: PubsApi = PubsApi.getInstance().create(PubsApi::class.java)

    val user= MutableLiveData<UserResponse>(null)

    val loading = MutableLiveData(false)

    fun signup(name: String, password: String, context: Context){
        if (isNetworkAvailable(context)){
            loading.postValue(true)
//            viewModelScope.launch {
//                val result = repository.apiUserCreate(name, password) //_api.signUp(BodySignUp(name, password))
//                if (result.body() != null){
//                    Log.d("testingViewModel: ", result.body().toString())
//                    if (result.code() == 200 && result.body()?.uid != -1){
//                        Toast.makeText(context,"Registration successful", Toast.LENGTH_SHORT).show()
//                    }
//                    else if (result.code() != 200) Toast.makeText(context,"Error code: ${result.code()}", Toast.LENGTH_SHORT).show()
//                    else Toast.makeText(context,"User already exists!", Toast.LENGTH_SHORT).show()
//
//                }
//
//                loading.postValue(false)
//            }
        }
    }

    fun signin(name: String, password: String, context: Context){
        if (isNetworkAvailable(context)){
            loading.postValue(true)
            viewModelScope.launch {
                repository.apiUserLogin(name, password, context) { user.postValue(it) }
                loading.postValue(false)
            }
        }
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        Toast.makeText(context,"No internet connection", Toast.LENGTH_SHORT).show()
        return false
    }
}