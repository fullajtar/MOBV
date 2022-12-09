package com.example.mobv.model

import android.os.Parcelable
import android.util.Log
import com.example.mobv.api.FriendResponse
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.android.parcel.Parcelize

data class Pubs (
    var elements: MutableList<Pub>? = null,
    var sortBy: String? = null //"ascending" or "descending"
){}

@Parcelize
data class Tags(
    var name : String? = null,
    var opening_hours: String? = null,
//    var opening_hourscovid19: String? = null,
    var website: String? = null,
    var phone: String? = null,
    val amenity: String? = null


): Parcelable

@Parcelize
data class Pub (
    val id: Number? = null,
    var lat: Number? = null,
    var lon: Number? = null,
    var tags: Tags? = null,
    var disFromLastPosition: Double? = null
): Parcelable

data class Coords (
    val lat: Double? = null,
    val lon: Double? = null
){}

object FriendsSingleton{
    var friends: MutableList<FriendResponse>? = null
}

object CloseBarsSingleton{
    var bars: MutableList<Pub>? = null

    fun purge(){
        val purgedBars = MutableList<Pub>(0) {Pub()}
        bars!!.forEach {
            if (it.tags?.name != null){
                Log.d("testingOut: ", "ID: ${it.id}")
                purgedBars.add(it)
            }
        }
        bars = purgedBars
    }

    private fun initDistance(lat: Double, lon: Double) {
        bars!!.forEach {
            if (it.tags?.name != null){
                val distance = SphericalUtil.computeDistanceBetween(
                    LatLng(lat, lon),
                    LatLng(it.lat!!.toDouble(), it.lon!!.toDouble())
                )
                it.disFromLastPosition = distance
            }
        }
    }

    fun sortByDistance(lat: Double, lon: Double){
        initDistance(lat, lon)
        bars!!.sortBy { it.disFromLastPosition }
    }


}

object BarsSingleton{
    var bars: MutableList<Bar>? = null
    var sortBy: String? = null

    fun initDistance(lat: Double, lon: Double) {
        BarsSingleton.bars!!.forEach {
            if (it.lat != null && it.lon != null){
                val distance = SphericalUtil.computeDistanceBetween(
                    LatLng(lat, lon),
                    LatLng(it.lat!!.toDouble(), it.lon!!.toDouble())
                )
                it.disFromLastPosition = distance
            }
        }
    }

    fun sortBy(){
        if (sortBy != null){
            if (sortBy!! == "ascending") {
                bars!!.sortBy { it.bar_name }
            }
            else if (sortBy!! == "descending") {
                bars!!.sortByDescending { it.bar_name }
            }
            else if (sortBy!! == "ascendingUsers") {
                bars!!.sortBy { it.users as Int }
            }
            else if (sortBy!! == "descendingUsers") {
                bars!!.sortByDescending { it.users as Int }
            }
            else if (sortBy!! == "ascendingDistance"){
                bars!!.sortBy { it.disFromLastPosition }
            }
            else if (sortBy!! == "descendingDistance"){
                bars!!.sortByDescending { it.disFromLastPosition }
            }
        }

    }

    fun toggleSortByName(){
        if (sortBy == null){
            sortBy = "ascending"
        }
        else if (sortBy.equals("ascending")){
            sortBy = "descending"
        }
        else{
            sortBy = "ascending"
        }
        sortBy()
    }

    fun toggleSortByUsers(){
        if (sortBy == null){
            sortBy = "ascendingUsers"
        }
        else if (sortBy.equals("ascendingUsers")){
            sortBy = "descendingUsers"
        }
        else{
            sortBy = "ascendingUsers"
        }
        sortBy()
    }

    fun toggleSortByDistance(){
        if (sortBy == null){
            sortBy = "ascendingDistance"
        }
        else if (sortBy.equals("ascendingDistance")){
            sortBy = "descendingDistance"
        }
        else{
            sortBy = "ascendingDistance"
        }
        sortBy()
    }
}

@Parcelize
data class Bar(
    var bar_id: String,
    var bar_name: String? = null,
    var lat: Double? = null,
    var lon: Double? = null,
    var bar_type: String? = null,
    var users: Int? = null,
    var disFromLastPosition:    Double? = null
): Parcelable


