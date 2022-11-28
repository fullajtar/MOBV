package com.example.mobv.model

import android.os.Parcelable
import com.example.mobv.api.FriendResponse
import kotlinx.android.parcel.Parcelize

object PubsSingleton{
    lateinit var pubs: Pubs
}

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
    var phone: String? = null


): Parcelable

@Parcelize
data class Pub (
    val id: Number? = null,
    var lat: Number? = null,
    var lon: Number? = null,
    var tags: Tags? = null
): Parcelable

object FriendsSingleton{
    var friends: MutableList<FriendResponse>? = null
}

object BarsSingleton{
    var bars: MutableList<Bar>? = null
    var sortBy: String? = null

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
        }
    }

    fun toggleSortByName(){
        if (sortBy == null){
            sortBy = "ascending"
        }
        else if (sortBy.equals("ascending")){
            sortBy = "descending"
        }
        else if (sortBy.equals("descending")){
            sortBy = "ascending"
        }
    }

    fun toggleSortByUsers(){
        if (sortBy == null){
            sortBy = "ascendingUsers"
        }
        else if (sortBy.equals("ascendingUsers")){
            sortBy = "descendingUsers"
        }
        else if (sortBy.equals("descendingUsers")){
            sortBy = "ascendingUsers"
        }
    }
}

@Parcelize
data class Bar(
    var bar_id: String? = null,
    var bar_name: String? = null,
    var lat: Number? = null,
    var lon: Number? = null,
    var bar_type: String? = null,
    var users: Number? = null,
): Parcelable


