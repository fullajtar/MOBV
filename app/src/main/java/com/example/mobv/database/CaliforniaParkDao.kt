/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.sqlbasics

import android.content.Context
import androidx.room.*

@Dao
interface CaliforniaParkDao {
    @Insert
    fun insertAll(parks: List<CaliforniaPark>)
    @Query("SELECT * FROM park")
    fun getAll(): List<CaliforniaPark>

//    @Insert
//    fun insertAllPubsDB(pubsDB: List<PubDB>)
//    @Query("SELECT * FROM pub")
//    fun getAllPubs(): List<PubDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPubsDB(pubsDB: List<PubDB>)
    @Query("SELECT * FROM bar")
    fun getAllPubs(): List<PubDB>

    companion object {
        //.allowMainThreadQueries is ONLY for countQueries,
        // do not use it anywhere else or it may potentially lock the UI for a long period of time.
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my_database"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
