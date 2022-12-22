package com.gulehri.emmiiplayer

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import java.util.*


// Created by Shahid Iqbal on 12/21/2022.

class MyApp : Application() {

    companion object{
        val version8OrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        val version12OrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
    }


    override fun onCreate() {
        super.onCreate()

        if (version8OrAbove) createChannel()

    }

    @SuppressLint("NewApi")
    private fun createChannel() {
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).apply {
            createNotificationChannel(NotificationChannel(
                "12",
                "MyChannel",
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setSound(null, null)
                })
        }
    }


}

