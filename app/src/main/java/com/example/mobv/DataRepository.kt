package com.example.mobv
//source: https://github.com/marosc/mobv2022

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.mobv.api.*
import com.example.mobv.model.Bar
import com.example.mobv.model.BarsSingleton
import com.example.mobv.model.FriendsSingleton
import com.example.mobv.model.Pubs
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DataRepository private constructor(
    private val service: PubsApi,
//    private val cache: LocalCache
){

    suspend fun apiUserCreate(
        name: String,
        password: String,
        context: Context,
        onStatus: (success: UserResponse?) -> Unit
    ) {
        try {
            val resp = service.signUp(BodySignUp(name = name, password = password))
            if (resp.isSuccessful) {
                resp.body()?.let { user ->
                    if (user.uid == (-1)){
                        onStatus(null)
                    }else {
                        Toast.makeText(context,"SignUp successful", Toast.LENGTH_SHORT).show()
                        onStatus(user)
                    }
                }
            } else {
                Toast.makeText(context,"Error code: ${resp.code()}", Toast.LENGTH_SHORT).show()
                onStatus(null)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onStatus(null)
        } catch (ex: Exception) {
            ex.printStackTrace()
            onStatus(null)
        }
    }

    suspend fun apiUserLogin(
        name: String,
        password: String,
        context: Context,
        onStatus: (success: UserResponse?) -> Unit
    ) {
        try {
            val resp = service.signIn(BodySignUp(name = name, password = password))
            if (resp.isSuccessful) {
                resp.body()?.let { user ->
                    if (user.uid == (-1)){
                        onStatus(null)
                        Toast.makeText(context ,"Incorrect username or password!", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(context,"SignIn successful", Toast.LENGTH_SHORT).show()
                        onStatus(user)
                    }
                }
            } else {
                Toast.makeText(context,"Error code: ${resp.code()}", Toast.LENGTH_SHORT).show()
                onStatus(null)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onStatus(null)
        } catch (ex: Exception) {
            ex.printStackTrace()
            onStatus(null)
        }
    }

    suspend fun apiBarCheckin(
        bar: BodyCheckInBar,
        context: Context
    ) {
        try {
            val resp = service.checkInBar(bar)
            if (resp.isSuccessful) {
                resp.body()?.let { user ->
                    Toast.makeText(context ,"Checked in successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context ,"Check in failed!", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    suspend fun apiBarList(): String {
        try {
            val resp = service.barList()
            if (resp.isSuccessful) {
                resp.body()?.let { bars ->
                    val b = bars.map {
                        Bar(
                            it.bar_id,
                            it.bar_name,
                            it.lat,
                            it.lon,
                            it.bar_type,
                            it.users,
                            0.0
                        )
                    }
                    BarsSingleton.bars = b as MutableList<Bar>

                    return "Success"
//                    cache.deleteBars()
//                    cache.insertBars(b)
                } //?: onError("Failed to load bars")
            } else {
//                onError("Failed to read bars")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
//            onError("Failed to load bars, check internet connection")
        } catch (ex: Exception) {
            ex.printStackTrace()
//            onError("Failed to load bars, error.")
        }
        return "Failure"
    }

    suspend fun apiNearbyBars(
        lat: Double, lon: Double,
    ) :Pubs{
        var nearby = Pubs()
        try {
            val q = "[out:json];node(around:250,$lat,$lon);(node(around:250)[\"amenity\"~\"^pub$|^bar$|^restaurant$|^cafe$|^fast_food$|^stripclub$|^nightclub$\"];);out body;>;out skel;"
            val resp = service.barNearby(q)
            if (resp.isSuccessful) {
                resp.body()?.let {
                    nearby = it
//                        bars ->
//                    nearby = bars.elements.map {
//                        NearbyBar(it.id,it.tags.getOrDefault("name",""), it.tags.getOrDefault("amenity",""),it.lat,it.lon,it.tags).apply {
//                            distance = distanceTo(MyLocation(lat,lon))
//                        }
//                    }
//                    nearby = nearby.filter { it.name.isNotBlank() }.sortedBy { it.distance }
                }
                Log.d("testingOut: ", "Nearby bars response: ${resp.code()} ${resp.body()}")
                Log.d("testingOut: ", "nearby: ${nearby}")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return nearby
    }
//
//    suspend fun apiBarDetail(
//        id: String,
//        onError: (error: String) -> Unit
//    ) : NearbyBar? {
//        var nearby:NearbyBar? = null
//        try {
//            val q = "[out:json];node($id);out body;>;out skel;"
//            val resp = service.barDetail(q)
//            if (resp.isSuccessful) {
//                resp.body()?.let { bars ->
//                    if (bars.elements.isNotEmpty()) {
//                        val b = bars.elements.get(0)
//                        nearby = NearbyBar(
//                            b.id,
//                            b.tags.getOrDefault("name", ""),
//                            b.tags.getOrDefault("amenity", ""),
//                            b.lat,
//                            b.lon,
//                            b.tags
//                        )
//                    }
//                } ?: onError("Failed to load bars")
//            } else {
//                onError("Failed to read bars")
//            }
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//            onError("Failed to load bars, check internet connection")
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//            onError("Failed to load bars, error.")
//        }
//        return nearby
//    }
//
//    fun dbBars() : LiveData<List<BarItem>?> {
//        return cache.getBars()
//    }

    suspend fun apiAddFriend(
        name: String,
        context: Context
    ) {
        try {
            val resp = service.addFriend(BodyAddFriend(name))
            if (resp.isSuccessful) {
                Toast.makeText(context,"Friend follow successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Error code: ${resp.code()}", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    suspend fun apiGetFriendList(
        context: Context
    ): String {
        try {
            val resp = service.getFriendList()
            if (!resp.isSuccessful){
                Toast.makeText(context,"Error code: ${resp.code()}", Toast.LENGTH_SHORT).show()
            }
            FriendsSingleton.friends = resp.body() as MutableList<FriendResponse>
            return "Success"

        } catch (ex: IOException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return "Failure"
    }

    companion object{
        @Volatile
        private var INSTANCE: DataRepository? = null

        fun getInstance(service: PubsApi, /**cache: LocalCache**/): DataRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: DataRepository(service, /**cache**/).also { INSTANCE = it }
            }

        @SuppressLint("SimpleDateFormat")
        fun dateToTimeStamp(date: String): Long {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date)?.time ?: 0L
        }

        @SuppressLint("SimpleDateFormat")
        fun timestampToDate(time: Long): String{
            val netDate = Date(time*1000)
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(netDate)
        }
    }
}