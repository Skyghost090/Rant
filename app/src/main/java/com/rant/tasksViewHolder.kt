package com.rant

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class tasksViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(tasks_: tasks) {
        itemView.findViewById<TextView>(R.id.taskName).text = tasks_.name
        itemView.findViewById<TextView>(R.id.taskInstructions).text = "${tasks_.instruction_} (Click to remove)"
    }
}