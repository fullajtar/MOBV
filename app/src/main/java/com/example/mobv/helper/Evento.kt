package com.example.mobv.helper

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * source: https://github.com/marosc/mobv2022
 */
open class Evento<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

}