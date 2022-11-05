package com.example.mobv

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobv.helper.DBHelper
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
//
//        // below code is to add on click
//        // listener to our add name button
//        addName.setOnClickListener{
//
//            // below we have created
//            // a new DBHelper class,
//            // and passed context to it
//            val db = DBHelper(this, null)
//
//            // creating variables for values
//            // in name and age edit texts
//            val name = enterName.text.toString()
//            val age = enterAge.text.toString()
//
//            // calling method to add
//            // name to our database
//            db.addName(name, age)
//
//            // Toast to message on the screen
//            Toast.makeText(this, name + " added to database", Toast.LENGTH_LONG).show()
//
//            // at last, clearing edit texts
//            enterName.text.clear()
//            enterAge.text.clear()
//        }
//
//        printName.setOnClickListener{
//
//            // creating a DBHelper class
//            // and passing context to it
//            val db = DBHelper(this, null)
//
//            // below is the variable for cursor
//            // we have called method to get
//            // all names from our database
//            // and add to name text view
//            val cursor = db.getName()
//
//            // moving the cursor to first position and
//            // appending value in the text view
//            cursor!!.moveToFirst()
//            Name.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)) + "\n")
//            Age.append(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n")
//
//            // moving our cursor to next
//            // position and appending values
//            while(cursor.moveToNext()){
//                Name.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)) + "\n")
//                Age.append(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n")
//            }
//
//            // at last we close our cursor
//            cursor.close()
//        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}