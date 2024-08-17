package com.rant

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jaredrummler.ktsh.Shell


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val floatButton_ = findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        val instructionText_ = findViewById<EditText>(R.id.instrucionText)
        val motivationText_ = findViewById<TextView>(R.id.textView)

        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES){
            instructionText_.setBackgroundResource(R.drawable.edittextdark)
            motivationText_.setBackgroundResource(R.drawable.edittextdark)
        } else {
            instructionText_.setBackgroundResource(R.drawable.edittextlight)
            motivationText_.setBackgroundResource(R.drawable.edittextlight)
        }

        floatButton_.setOnClickListener{
            val notification_ = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val ChannelID = "id"
            val notificationChannel = NotificationChannel(ChannelID,
                "Rant",
                NotificationManager.IMPORTANCE_DEFAULT);
            notification_.createNotificationChannel(notificationChannel);

            val builder = NotificationCompat.Builder(this, ChannelID);

            builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Tasks").setContentText("${instructionText_.text}")
                .setOngoing(true)
                .setAutoCancel(false)
            notification_.notify(1, builder.build())
        }
    }
}