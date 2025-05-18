package com.rant

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class NotificationTools {
    fun startNotification(context_: Context){
        val serviceIntent = Intent(context_, notificationService::class.java)
        ContextCompat.startForegroundService(context_, serviceIntent)
    }
    fun notifySend(context_: Context, notification_: NotificationManager, values: Array<String>){
        var id = 0
        val limitTaskList_ = values.size - 1
        for (i in 0..limitTaskList_){
            val ChannelID = "$id"
            val builder = NotificationCompat.Builder(context_, ChannelID)
            val notificationChannel = NotificationChannel(ChannelID,
                values[i],
                NotificationManager.IMPORTANCE_DEFAULT)
            notification_.createNotificationChannel(notificationChannel)
            id++
            builder.setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle(values[i])
                .setContentText("")
                .setOngoing(true)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setSilent(true)

            notification_.notify(id, builder.build())
        }
    }
}