package com.rant

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.os.SystemClock.sleep
import androidx.core.app.NotificationCompat
import kotlin.system.exitProcess


class notificationService : Service() {
    private fun run_(){
        val values = getSharedPreferences("tasks", MODE_PRIVATE).all.keys.toTypedArray()
        val instructions = getSharedPreferences("tasks", MODE_PRIVATE).all.values.toTypedArray()
        val limitTaskList_ = values.size - 1
        var id = 0
        for (i in 0..limitTaskList_){
            val notification_ = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val ChannelID = "$id"
            val builder = NotificationCompat.Builder(this, ChannelID);
            val notificationChannel = NotificationChannel(ChannelID,
                values[i].toString(),
                NotificationManager.IMPORTANCE_DEFAULT);
            notification_.createNotificationChannel(notificationChannel)
            id++
            builder.setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle(values[i].toString()).setContentText(instructions[i].toString())
                .setOngoing(true)
                    .setAutoCancel(false)
                    .setPriority(NotificationCompat.PRIORITY_MIN)
                    .setSilent(true)

            notification_.notify(id, builder.build())
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { onTaskRemoved(it) }
        run_()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        Thread{
            sleep(5000)
            val restartServiceIntent = Intent(applicationContext, this.javaClass)
            restartServiceIntent.setPackage(packageName)
            startService(restartServiceIntent)
            super.onTaskRemoved(rootIntent)
        }.start()
    }
}