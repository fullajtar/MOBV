package com.example.mobv

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mobv.model.Pubs
import com.example.mobv.model.PubsSingleton
import com.example.mobv.retrofit.RetrofitHelper
import com.example.sqlbasics.AppDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.mobv.retrofit.PubsApi
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

        val quotesApi = RetrofitHelper.getInstance().create(PubsApi::class.java)
        // launching a new coroutine
        GlobalScope.launch {
            val result = quotesApi.getQuotes()
            if (result != null)
            // Checking the results
                Log.d("ayush: ", result.body().toString())
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}