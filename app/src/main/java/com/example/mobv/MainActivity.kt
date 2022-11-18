package com.example.mobv

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.mobv.model.Pubs
import com.example.mobv.model.PubsSingleton
import com.example.mobv.retrofit.BodyApi
import com.example.mobv.retrofit.RetrofitHelper
import com.example.sqlbasics.AppDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.mobv.retrofit.PubsApi
import com.example.sqlbasics.PubDB
import retrofit2.create

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //read raw json file as string
        val jsonString = applicationContext.resources.openRawResource(
            applicationContext.resources.getIdentifier(
                "pubs",
                "raw",
                applicationContext.packageName
            )
        ).bufferedReader().use { it.readText() }

        //convert json string to obj
        val gson = Gson()
        val sType = object : TypeToken<Pubs>() { }.type
        val pubs = gson.fromJson<Pubs>(jsonString, sType)
        PubsSingleton.pubs = pubs

        GlobalScope.launch {
            AppDatabase.getDatabase(applicationContext).californiaParkDao().getAll()
        }

        if (isNetworkAvailable(applicationContext)){
            val quotesApi = RetrofitHelper.getInstance().create(PubsApi::class.java)
            // launching a new coroutine
            GlobalScope.launch {

                val result = quotesApi.getPubs(BodyApi())
                if (result != null)
                // Checking the results
                    Log.d("ayush: ", result.body().toString())
            }
        }
        else{
            Log.d("ayush: ", "net NOT available")
        }



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        return false
    }
}