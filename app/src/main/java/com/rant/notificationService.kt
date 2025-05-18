package com.rant

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock.sleep

class notificationService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val values = getSharedPreferences("tasks", MODE_PRIVATE).all.keys.toTypedArray()
        val notification_ = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        intent?.let { onTaskRemoved(it) }
        NotificationTools().notifySend(applicationContext, notification_, values)
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