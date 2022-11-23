package com.example.mobv.helper
//source: https://github.com/marosc/mobv2022

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.mobv.DataRepository
import com.example.mobv.api.PubsApi

object Injection {
//    private fun provideCache(context: Context): LocalCache {
//        val database = AppRoomDatabase.getInstance(context)
//        return LocalCache(database.appDao())
//    }

    fun provideDataRepository(context: Context): DataRepository {
        return DataRepository.getInstance(PubsApi.create(context),/** provideCache(context) **/)
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(
            provideDataRepository(
                context
            )
        )
    }
}