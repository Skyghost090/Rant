package com.rant

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri

class Clicks {
    fun floatButton(instructionText_: String, sharedPrefs: SharedPreferences){
        if (instructionText_ != ""){
            val tasksPrefs = sharedPrefs.edit()
            tasksPrefs.putString(instructionText_,"Click to Remove")
            tasksPrefs.apply()
        }
    }
    fun aboutButton(): Intent? {
        val sharingIntent = Intent(Intent.ACTION_VIEW)
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        sharingIntent.setData(Uri.parse("https://github.com/Skyghost090"))
        val chooserIntent = Intent.createChooser(sharingIntent, "Open With")
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return chooserIntent
    }
}