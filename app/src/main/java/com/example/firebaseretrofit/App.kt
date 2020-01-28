package com.example.firebaseretrofit

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.location.Location
import android.os.Build
import java.lang.Exception

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        res = resources
    }




    companion object {
        var instance: App? = null
        var res: Resources? = null

        fun getContext(): Context {
            return instance!!.applicationContext
        }

        fun getResourses(): Resources {
            return res!!
        }
    }
}