package com.example.callapiexamplerx

import android.util.Log

fun printLog(message: Any?, prefix: String = "") {
    if (message == null || !BuildConfig.DEBUG) return
    val stackTraceElement = Thread.currentThread().stackTrace[4]
    Log.d("[MiiLog${prefix} - ${stackTraceElement.fileName}]", "#$message")

}