package com.example.mobv

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mobv.model.Pubs
import com.example.mobv.model.PubsSingleton
import com.example.sqlbasics.AppDatabase
import com.example.sqlbasics.PubDB
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
            var out = AppDatabase.getDatabase(applicationContext).californiaParkDao().getAllPubs()
            Log.d("testingOut: ", out.toString())

            val pub0 = PubDB(3, "50", "100")
            val pub1 = PubDB(4,"20", "222")
//            crashing the app, probably beacuse im tryin gto add entries with existing ID
//            AppDatabase.getDatabase(applicationContext).californiaParkDao().insertAllPubsDB(listOf(pub0, pub1))

            out = AppDatabase.getDatabase(applicationContext).californiaParkDao().getAllPubs()
            Log.d("testingOut: ", out.toString())

        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}