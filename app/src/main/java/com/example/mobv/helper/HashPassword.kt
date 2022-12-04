package com.example.mobv.helper

import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

object HashPassword {
    fun hashPassword(passwordToHash :String):String?{

        val salt: String = "randomSalt"
        var generatedPassword: String? = null
        try {
            val md: MessageDigest = MessageDigest.getInstance("SHA-512")
            md.update(salt.toByteArray())
            val bytes: ByteArray = md.digest(passwordToHash.toByteArray())
            val sb = StringBuilder()
            for (i in bytes.indices) {
                sb.append(
                    Integer.toString((bytes[i] and 0xff.toByte()) + 0x100, 16)
                        .substring(1)
                )
            }
            generatedPassword = sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        Log.d("testingOut: ", "Password: ${generatedPassword}")
        return generatedPassword
//        return passwordToHash
    }
}