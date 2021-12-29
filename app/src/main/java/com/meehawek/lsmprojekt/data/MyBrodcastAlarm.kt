package com.meehawek.lsmprojekt.data

import com.meehawek.lsmprojekt.R
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class MyBroadcastAlarm : BroadcastReceiver() {

    private fun simpleNotification(context: Context) {

        val builder = NotificationCompat.Builder(context.applicationContext, "20")
            .setContentTitle("Hej! Nowe wyniki w Żyniku!")
            .setContentText("Pobraliśmy najświeższe wyniki Twoich ulubieńców :)")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(1, builder.build())
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            Log.d("Alarm ran", "Alarm just fired")

            // updateDb()
            simpleNotification(context)
        }
    }
}