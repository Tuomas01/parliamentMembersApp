package com.example.project

import android.app.Application
import android.content.Context

//Singleton used for the database creation and getting context
class MyApp : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}