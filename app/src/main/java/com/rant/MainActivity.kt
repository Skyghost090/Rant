package com.rant

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom + (imeInsets.bottom * 1.0).roundToInt())
            insets
        }
        val floatButton_ = findViewById<ImageView>(R.id.floatingbutton)
        val instructionText_ = findViewById<EditText>(R.id.instrucionText)
        val aboutBtn = findViewById<ImageView>(R.id.imageView)
        val tasksListWidget_ = findViewById<RecyclerView>(R.id.recyclerView)

        tasksListWidget_.layoutManager = LinearLayoutManager(this)
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = LengthFilter(32)
        instructionText_.setFilters(filterArray)

        NotificationTools().startNotification(applicationContext)
        fun readTasksDB(){
            val values = getSharedPreferences("tasks", MODE_PRIVATE).all.keys.toTypedArray()
            val instructions = getSharedPreferences("tasks", MODE_PRIVATE).all.values.toTypedArray()
            val taskslist = ArrayList<tasks>()
            val limitTaskList_ = values.size - 1
            for (i in 0..limitTaskList_){
                taskslist.add(tasks(values[i].toString(), instructions[i].toString()))
            }
            if(values.isEmpty()){
                taskslist.add(tasks("Welcome to Rant \uD83D\uDE0A", "App for to innovate the way you work \uD83D\uDE80"))
                taskslist.add(tasks("There are no tasks ✅", "Please type a content below and click in add"))
                taskslist.add(tasks("Please Support us ❤\uFE0F", "Click in About Button to access our website \uD83D\uDE4F"))
            }
            val adapter = tasksAdapter(taskslist)
            tasksListWidget_.adapter = adapter
            if (values.isNotEmpty()){
                adapter.setOnClickListener(
                    object :
                        tasksAdapter.OnClickListener{
                            override fun onClick(position: Int, model: tasks) {
                                taskslist.remove(taskslist[position])
                                adapter.notifyItemRemoved(position)
                                getSharedPreferences("tasks", MODE_PRIVATE).edit().remove(values[position]).apply()
                                readTasksDB()
                            }
                        }
                    )
            }
        }
        readTasksDB()

        floatButton_.setOnClickListener {
            Clicks().floatButton(instructionText_.text.toString(), getSharedPreferences("tasks", MODE_PRIVATE))
            readTasksDB()
        }
        aboutBtn.setOnClickListener {
            startActivity(Clicks().aboutButton())
        }
    }
}
