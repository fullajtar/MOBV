package com.example.mobv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.mobv.databinding.FragmentRegistrationBinding
import com.example.mobv.model.Pubs
import com.example.mobv.model.PubsSingleton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}