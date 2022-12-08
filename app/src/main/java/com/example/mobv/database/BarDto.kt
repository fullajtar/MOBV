package com.example.mobv.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mobv.model.Bar
import com.example.mobv.model.BarsSingleton
import com.example.mobv.model.PubsSingleton.pubs
import com.example.sqlbasics.PubDB

    fun fromDBtoSingleton(pubs: List<PubDB>){
        val bars = mutableListOf<Bar>()
        pubs.map{
            bars += it.toBar()
        }
        Log.d("DTO list from DB", "bars: ${bars} ")
        BarsSingleton.bars = bars
    }
    fun fromSingletonToDB():List<PubDB>{
        val bars = BarsSingleton.bars as List<Bar>
        val pubs = mutableListOf<PubDB>()
        bars.map{
            pubs += it.toPubDB()
        }
        Log.d("DTO list", "pub: ${pubs} ")
        return pubs
    }

    fun Bar.toPubDB() = PubDB(
        id = bar_id,
        bar_name = bar_name,
        lat = lat,
        lon = lon,
        bar_type = bar_type,
        users = users,
        disFromLastPosition = disFromLastPosition

    )

    fun PubDB.toBar() = Bar(
        bar_id = id,
        bar_name = bar_name,
        lat = lat,
        lon = lon,
        bar_type = bar_type,
        users = users,
        disFromLastPosition = disFromLastPosition
    )
