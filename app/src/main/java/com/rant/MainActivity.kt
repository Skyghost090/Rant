package com.rant

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        val titleText_ = findViewById<EditText>(R.id.titleText)
        val tablayout = findViewById<TabLayout>(R.id.tabLayout)
        val githubAutor = findViewById<ImageView>(R.id.imageView)
        val appTitle_ = findViewById<TextView>(R.id.textView2)

        fun startNotification(){
            val serviceIntent = Intent(this, notificationService::class.java)
            ContextCompat.startForegroundService(this, serviceIntent)
        }
        startNotification()

        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES){
            instructionText_.setBackgroundResource(R.drawable.edittextdark)
            motivationText_.setBackgroundResource(R.drawable.edittextdark)
            titleText_.setBackgroundResource(R.drawable.edittextdark)
        } else {
            instructionText_.setBackgroundResource(R.drawable.edittextlight)
            motivationText_.setBackgroundResource(R.drawable.edittextlight)
            titleText_.setBackgroundResource(R.drawable.edittextlight)
        }

        fun detectTab() {
            when(tablayout.selectedTabPosition) {
                0 -> {
                    appTitle_.isVisible = true
                    titleText_.isVisible = true
                    githubAutor.isVisible = false
                    instructionText_.isVisible = true
                    floatButton_.setImageResource(android.R.drawable.ic_input_add)
                    floatButton_.setOnClickListener {
                        val sharedPrefs = getSharedPreferences("tasks", MODE_PRIVATE)
                        val tasksPrefs = sharedPrefs.edit()
                        tasksPrefs.putString(titleText_.text.toString(),instructionText_.text.toString())
                        tasksPrefs.apply()
                    }
                    motivationText_.setText(R.string.motivation_text)
                    motivationText_.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                }
                1 -> {
                    startActivity(Intent(this, tasksList::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).apply {
                        putExtra("keyIdentifier", "value")
                    })
                    tablayout.selectTab(tablayout.getTabAt(0))
                }
                2 -> {
                    appTitle_.isVisible = false
                    titleText_.isVisible = false
                    instructionText_.isVisible = false
                    githubAutor.isVisible = true
                    floatButton_.setImageResource(R.drawable.ic_action_github)
                    floatButton_.setOnClickListener {
                        val builder = CustomTabsIntent.Builder()
                        builder.setInstantAppsEnabled(true)
                        builder.setDownloadButtonEnabled(false)
                        val customBuilder = builder.build()
                        customBuilder.intent.setPackage("com.android.chrome")
                        customBuilder.launchUrl(this, Uri.parse("https://github.com/Skyghost090"))
                    }
                    motivationText_.setText(R.string.about_text)
                    motivationText_.textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
            }
        }

        detectTab()

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {detectTab()}
            override fun onTabUnselected(tab: TabLayout.Tab) {detectTab()}
            override fun onTabReselected(tab: TabLayout.Tab) {detectTab()}
        })
    }
}