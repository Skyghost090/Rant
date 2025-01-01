package com.rant

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class tasksList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        enableEdgeToEdge()
        setContentView(R.layout.activity_tasks_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tasksListWidget_ = findViewById<RecyclerView>(R.id.tasksList)
        tasksListWidget_.layoutManager = LinearLayoutManager(this)

        val values = getSharedPreferences("tasks", MODE_PRIVATE).all.keys.toTypedArray()
        val instructions = getSharedPreferences("tasks", MODE_PRIVATE).all.values.toTypedArray()
        val taskslist = ArrayList<tasks>()
        val limitTaskList_ = values.size - 1
        for (i in 0..limitTaskList_){
            taskslist.add(tasks(values[i].toString(), instructions[i].toString()))
        }
        val adapter = tasksAdapter(taskslist)
        tasksListWidget_.adapter = adapter
        adapter.setOnClickListener(
            object :
                tasksAdapter.OnClickListener{
                    override fun onClick(position: Int, model: tasks) {
                        taskslist.remove(taskslist[position])
                        adapter.notifyItemRemoved(position)
                        getSharedPreferences("tasks", MODE_PRIVATE).edit().remove(values[position]).apply()
                    }
                }
        )


        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES){
            tasksListWidget_.setBackgroundResource(R.drawable.edittextdark)
        } else {
            tasksListWidget_.setBackgroundResource(R.drawable.edittextlight)
        }
    }
}
