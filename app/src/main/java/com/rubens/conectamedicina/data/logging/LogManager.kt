package com.rubens.conectamedicina.data.logging

import android.util.Log

class LogManager {
    fun printErrorLogs(tag: String, errorMessage: String){
        Log.e(tag, errorMessage)

    }
}