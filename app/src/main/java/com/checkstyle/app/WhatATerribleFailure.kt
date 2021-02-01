package com.checkstyle.app

import android.util.Log

@Suppress("unused")
class WhatATerribleFailure {
    fun <T> logAsWtf(clazz: Class<T>, message: String) {
        Log.wtf(clazz.name, message)

       // printMessage(message)
    }

    fun printMessage(message: String) {
        Log.d("TAG", message)
    }
}
